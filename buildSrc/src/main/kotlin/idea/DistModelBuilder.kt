package org.jetbrains.kotlin.buildUtils.idea

import IntelliJInstrumentCodeTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import idea.DistCopyDetailsMock
import idea.DistModelBuildContext
import org.codehaus.groovy.runtime.GStringImpl
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileVisitDetails
import org.gradle.api.file.FileVisitor
import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.CompositeFileCollection
import org.gradle.api.internal.file.FileCollectionInternal
import org.gradle.api.internal.file.FileCollectionVisitor
import org.gradle.api.internal.file.FileTreeInternal
import org.gradle.api.internal.file.archive.ZipFileTree
import org.gradle.api.internal.file.collections.DirectoryFileTree
import org.gradle.api.internal.file.collections.FileTreeAdapter
import org.gradle.api.internal.file.copy.CopySpecInternal
import org.gradle.api.internal.file.copy.DefaultCopySpec
import org.gradle.api.internal.file.copy.DestinationRootCopySpec
import org.gradle.api.internal.file.copy.SingleParentCopySpec
import org.gradle.api.tasks.AbstractCopyTask
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.SourceSetOutput
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.api.tasks.compile.AbstractCompile
import org.gradle.jvm.tasks.Jar
import java.io.File
import java.io.PrintWriter
import java.util.concurrent.Callable

class DistModelBuilder(val rootProject: Project, pw: PrintWriter) {
    val rootCtx = DistModelBuildContext(null, "ROOT", "dist", pw)
    val visited = mutableMapOf<Task, DistModelBuildContext>()
    val vfsRoot = DistVFile(null, "<root>", File(""))
    val refs = mutableSetOf<DistVFile>()

    fun visitInterumentTask(it: IntelliJInstrumentCodeTask): DistModelBuildContext = visited.getOrPut(it) {
        // todo:
        val ctx = rootCtx.child("INSTRUMENT", it.path)
        ctx.setDest(it.output!!.path)
        processSourcePath(it.originalClassesDirs, ctx)
        val dest = ctx.destination
//            val sourceSet = it.sourceSet
        if (dest != null) {
            DistModuleOutput(dest, it.project.path)
        }

        ctx
    }

    fun visitCompileTask(it: AbstractCompile): DistModelBuildContext = visited.getOrPut(it) {
        val ctx = rootCtx.child("COMPILE", it.path)
        ctx.setDest(it.destinationDir.path)
        val dest = ctx.destination
        if (dest != null) DistModuleOutput(dest, it.project.path)
        else ctx.logUnsupported("Cannot add contents: destination is unknown", it)

        ctx
    }

    fun visitCopyTask(
            copy: AbstractCopyTask,
            shade: Boolean = false
    ): DistModelBuildContext = visited.getOrPut(copy) {
        val context = rootCtx.child("COPY", copy.path, shade)


        val rootSpec = copy.rootSpec

        when (copy) {
            is Copy -> context.setDest(copy.destinationDir.path)
            is Sync -> context.setDest(copy.destinationDir.path)
            is AbstractArchiveTask -> context.setDest(copy.archivePath.path)
        }

        when (copy) {
            is ShadowJar -> copy.configurations.forEach {
                processSourcePath(it, context)
            }
        }

        processCopySpec(rootSpec, context)


        context
    }

    fun processCopySpec(spec: CopySpecInternal, ctx: DistModelBuildContext) {
        spec.children.forEach {
            when (it) {
                is DestinationRootCopySpec -> ctx.child("DESTINATION ROOT COPY SPEC") { newCtx ->
                    newCtx.setDest(getRelativePath(it.destinationDir.path))
                    processCopySpec(it, newCtx)
                }
                is DefaultCopySpec -> ctx.child("DEFAULT COPY SPEC") { newCtx ->
                    val buildRootResolver = it.buildRootResolver()
                    ctx.addCopyActions(buildRootResolver.allCopyActions)
                    newCtx.setDest(buildRootResolver.destPath.getFile(ctx.destination!!.file).path)
                    processCopySpec(it, newCtx)
                    it.includes

                    newCtx.child("SINGE PARENT COPY SPEC") { child ->
                        it.sourcePaths.forEach {
                            processSourcePath(it, child)
                        }
                    }
                }
                is SingleParentCopySpec -> ctx.child("OTHER SINGE PARENT COPY SPEC") { child ->
                    it.sourcePaths.forEach {
                        processSourcePath(it, child)
                    }
                }
                is CopySpecInternal -> processCopySpec(it, ctx)
                else -> ctx.logUnsupported("CopySpec", spec)
            }
        }
    }

    fun processSourcePath(it: Any?, ctx: DistModelBuildContext) {
        when {
            it == null -> Unit
            it is Jar -> ctx.child("JAR") { child ->
                child.addCopyOf(it.archivePath.path)
            }
            it is SourceSetOutput -> ctx.child("COMPILE") { child ->
                it.classesDirs.files.forEach {
                    child.addCopyOf(it.path)
                }
            }
            it is Configuration -> {
                ctx.child("CONFIGURATION") { child ->
                    it.resolve().forEach {
                        child.addCopyOf(it.path)
                    }
                }
            }
            it is SourceDirectorySet -> {
                ctx.child("SOURCES") { child ->
                    it.srcDirs.forEach {
                        child.addCopyOf(it.path)
                    }
                }
            }
            it is CompositeFileCollection -> ctx.child("COMPOSITE FILE COLLECTION") { child ->
                it.visitRootElements(object : FileCollectionVisitor {
                    override fun visitDirectoryTree(directoryTree: DirectoryFileTree?) {
                        child.logUnsupported("DIR TREE", directoryTree)
                    }

                    override fun visitTree(fileTree: FileTreeInternal?) {
                        child.logUnsupported("TREE", fileTree)
                    }

                    override fun visitCollection(fileCollection: FileCollectionInternal?) {
                        processSourcePath(fileCollection, child)
                    }
                })
            }
            it is FileTreeAdapter && it.tree is ZipFileTree -> ctx.child("ZIP FILE TREE ADAPTER") { child ->
                val tree = it.tree
                val field = tree.javaClass.declaredFields.find { it.name == "zipFile" }!!
                field.isAccessible = true
                val zipFile = field.get(tree) as File

                child.addCopyOf(zipFile.path)
            }
            it is FileTreeInternal -> ctx.child("FILE TREE INTERNAL") { child ->
                // todo: preserve or warn about filtering
                it.visitTreeOrBackingFile(object : FileVisitor {
                    override fun visitFile(fileDetails: FileVisitDetails) {
                        child.addCopyOf(fileDetails.file.path)
                    }

                    override fun visitDir(dirDetails: FileVisitDetails) {
                        child.addCopyOf(dirDetails.file.path)
                    }
                })
            }
            it is FileCollection -> ctx.child("OTHER FILE COLLECTION") { child ->
                try {
                    it.files.forEach {
                        child.addCopyOf(it.path)
                    }
                } catch (t: Throwable) {
                    child.logUnsupported("FILE COLLECTION (${t.message})", it)
                }
            }
            it is String || it is GStringImpl -> ctx.child("STRING") { child ->
                child.addCopyOf(it.toString())
            }
            it is Callable<*> -> ctx.child("CALLABLE") { child ->
                processSourcePath(it.call(), child)
            }
            it is Collection<*> -> ctx.child("COLLECTION") { child ->
                it.forEach {
                    processSourcePath(it, child)
                }
            }
            it is Copy -> ctx.child("COPY OUTPUT") { child ->
                val src = visitCopyTask(it).destination
                if (src != null) child.addCopyOf(src)
                // else it is added to `it`, because destination is inhereted by context
            }
            it is File -> ctx.child("FILE") { child ->
                child.addCopyOf(it.path)
            }
            else -> ctx.logUnsupported("SOURCE PATH", it)
        }
    }

    inline fun DistModelBuildContext.addCopyOf(
            src: String,
            body: (src: DistVFile, target: DistVFile) -> Unit = { _, _ -> Unit }
    ) {
        addCopyOf(requirePath(src), body)
    }

    fun DistModelBuildContext.transformName(srcName: String): String? {
        val detailsMock = DistCopyDetailsMock(srcName)
        allCopyActions.forEach {
            try {
                it.execute(detailsMock)
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
        val name1 = detailsMock.relativePath.lastName
        return if (name1.endsWith(".jar")) transformJarName(name1) else name1
    }

    // todo: investigate why allCopyActions not working
    private fun transformJarName(name: String): String {
        val name1 = name.replace(Regex("-${java.util.regex.Pattern.quote(rootProject.version.toString())}"), "")

        val name2 = when (name1) {
            "kotlin-runtime-common.jar" -> "kotlin-runtime.jar"
            "kotlin-compiler-before-proguard.jar" -> "kotlin-compiler.jar"
            "kotlin-allopen-compiler-plugin.jar" -> "allopen-compiler-plugin.jar"
            "kotlin-noarg-compiler-plugin.jar" -> "noarg-compiler-plugin.jar"
            "kotlin-sam-with-receiver-compiler-plugin.jar" -> "sam-with-receiver-compiler-plugin.jar"
            "kotlin-android-extensions-runtime.jar" -> "android-extensions-runtime.jar"
            else -> name1
        }

        val name3 = name2.removePrefix("dist-")

        return name3
    }


    inline fun DistModelBuildContext.addCopyOf(
            src: DistVFile,
            body: (src: DistVFile, target: DistVFile) -> Unit = { _, _ -> Unit }
    ) {
        if (src.file.path.contains("build/tmp")) return

        val destination = destination
        if (destination != null) {
            body(src, destination)
            val customTargetName = transformName(src.name)
            DistCopy(destination, src, customTargetName)
            log("+DistCopy", "${getRelativePath(src.file.path)} -> ${getRelativePath(destination.file.path)}/$customTargetName")
        } else logUnsupported("Cannot add copy of `$src`: destination is unknown")
    }

    fun DistModelBuildContext.setDest(path: String) {
        destination = vfsRoot.relativePath(path)
        log("INTO", getRelativePath(path))
    }

    fun checkRefs() {
        refs.forEach {
            if (!it.hasContents && it.contents.isEmpty() && it.file.path.contains("/build/")) {
                println("UNRESOLVED ${it.file}")
                it.contents.forEach {
                    println("+ ${it}")
                }
            }
        }
    }

    fun getRelativePath(path: String) = path.replace(rootProject.projectDir.path, "$")

    fun requirePath(targetPath: String): DistVFile {
        val target = vfsRoot.relativePath(targetPath)
        if (!File(targetPath).exists()) refs.add(target)
        return target
    }
}