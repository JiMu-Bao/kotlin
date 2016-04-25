/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jetbrains.kotlin.idea.intentions;

import com.intellij.testFramework.TestDataPath;
import org.jetbrains.kotlin.test.JUnit3RunnerWithInners;
import org.jetbrains.kotlin.test.KotlinTestUtils;
import org.jetbrains.kotlin.test.TestMetadata;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.regex.Pattern;

/** This class is generated by {@link org.jetbrains.kotlin.generators.tests.TestsPackage}. DO NOT MODIFY MANUALLY */
@SuppressWarnings("all")
@TestMetadata("idea/testData/multiFileIntentions")
@TestDataPath("$PROJECT_ROOT")
@RunWith(JUnit3RunnerWithInners.class)
public class MultiFileIntentionTestGenerated extends AbstractMultiFileIntentionTest {
    public void testAllFilesPresentInMultiFileIntentions() throws Exception {
        KotlinTestUtils.assertAllTestsPresentInSingleGeneratedClass(this.getClass(), new File("idea/testData/multiFileIntentions"), Pattern.compile("^(.+)\\.test$"));
    }

    @TestMetadata("convertMemberToExtension/addImports/addImports.test")
    public void testConvertMemberToExtension_addImports_AddImports() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/convertMemberToExtension/addImports/addImports.test");
        doTest(fileName);
    }

    @TestMetadata("implementAbstractMember/implementFunctionInJava/implementAllInJava.test")
    public void testImplementAbstractMember_implementFunctionInJava_ImplementAllInJava() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/implementAbstractMember/implementFunctionInJava/implementAllInJava.test");
        doTest(fileName);
    }

    @TestMetadata("implementAbstractMember/implementValInJava/implementAllInJava.test")
    public void testImplementAbstractMember_implementValInJava_ImplementAllInJava() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/implementAbstractMember/implementValInJava/implementAllInJava.test");
        doTest(fileName);
    }

    @TestMetadata("implementAbstractMember/implementVarInJava/implementAllInJava.test")
    public void testImplementAbstractMember_implementVarInJava_ImplementAllInJava() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/implementAbstractMember/implementVarInJava/implementAllInJava.test");
        doTest(fileName);
    }

    @TestMetadata("implementAsConstructorParameter/implementValInJava/implementAllInJava.test")
    public void testImplementAsConstructorParameter_implementValInJava_ImplementAllInJava() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/implementAsConstructorParameter/implementValInJava/implementAllInJava.test");
        doTest(fileName);
    }

    @TestMetadata("moveDeclarationToSeparateFile/moveClassToExistingFile/moveClassToExistingFile.test")
    public void testMoveDeclarationToSeparateFile_moveClassToExistingFile_MoveClassToExistingFile() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveDeclarationToSeparateFile/moveClassToExistingFile/moveClassToExistingFile.test");
        doTest(fileName);
    }

    @TestMetadata("moveDeclarationToSeparateFile/moveClassToFile/moveClassToFile.test")
    public void testMoveDeclarationToSeparateFile_moveClassToFile_MoveClassToFile() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveDeclarationToSeparateFile/moveClassToFile/moveClassToFile.test");
        doTest(fileName);
    }

    @TestMetadata("moveDeclarationToSeparateFile/moveSingleToFile/moveSingleToFile.test")
    public void testMoveDeclarationToSeparateFile_moveSingleToFile_MoveSingleToFile() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveDeclarationToSeparateFile/moveSingleToFile/moveSingleToFile.test");
        doTest(fileName);
    }

    @TestMetadata("moveDeclarationToSeparateFile/optimizeImports/optimizeImports.test")
    public void testMoveDeclarationToSeparateFile_optimizeImports_OptimizeImports() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveDeclarationToSeparateFile/optimizeImports/optimizeImports.test");
        doTest(fileName);
    }

    @TestMetadata("moveFileToPackageMatchingDirectory/moveToDefaultDirectory/moveToDefaultDirectory.test")
    public void testMoveFileToPackageMatchingDirectory_moveToDefaultDirectory_MoveToDefaultDirectory() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveFileToPackageMatchingDirectory/moveToDefaultDirectory/moveToDefaultDirectory.test");
        doTest(fileName);
    }

    @TestMetadata("moveFileToPackageMatchingDirectory/moveToNonDefaultDirectory/moveToNonDefaultDirectory.test")
    public void testMoveFileToPackageMatchingDirectory_moveToNonDefaultDirectory_MoveToNonDefaultDirectory() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveFileToPackageMatchingDirectory/moveToNonDefaultDirectory/moveToNonDefaultDirectory.test");
        doTest(fileName);
    }

    @TestMetadata("moveFileToPackageMatchingDirectory/packageMatchesDirectory/packageMatchesDirectory.test")
    public void testMoveFileToPackageMatchingDirectory_packageMatchesDirectory_PackageMatchesDirectory() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveFileToPackageMatchingDirectory/packageMatchesDirectory/packageMatchesDirectory.test");
        doTest(fileName);
    }

    @TestMetadata("moveOutOfCompanion/moveClass/moveClass.test")
    public void testMoveOutOfCompanion_moveClass_MoveClass() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveOutOfCompanion/moveClass/moveClass.test");
        doTest(fileName);
    }

    @TestMetadata("moveToCompanion/moveFunction/moveFunction.test")
    public void testMoveToCompanion_moveFunction_MoveFunction() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveToCompanion/moveFunction/moveFunction.test");
        doTest(fileName);
    }

    @TestMetadata("moveToCompanion/moveInnerClass/moveInnerClass.test")
    public void testMoveToCompanion_moveInnerClass_MoveInnerClass() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveToCompanion/moveInnerClass/moveInnerClass.test");
        doTest(fileName);
    }

    @TestMetadata("moveToCompanion/moveNonInnerClass/moveNonInnerClass.test")
    public void testMoveToCompanion_moveNonInnerClass_MoveNonInnerClass() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveToCompanion/moveNonInnerClass/moveNonInnerClass.test");
        doTest(fileName);
    }

    @TestMetadata("moveToCompanion/moveProperty/moveProperty.test")
    public void testMoveToCompanion_moveProperty_MoveProperty() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/moveToCompanion/moveProperty/moveProperty.test");
        doTest(fileName);
    }

    @TestMetadata("objectLiteralToLambda/objectLiteralToLambda.test")
    public void testObjectLiteralToLambda_ObjectLiteralToLambda() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/objectLiteralToLambda/objectLiteralToLambda.test");
        doTest(fileName);
    }

    @TestMetadata("reconcilePackageWithDirectory/changeToDefaultPackage/changeToDefaultPackage.test")
    public void testReconcilePackageWithDirectory_changeToDefaultPackage_ChangeToDefaultPackage() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/reconcilePackageWithDirectory/changeToDefaultPackage/changeToDefaultPackage.test");
        doTest(fileName);
    }

    @TestMetadata("reconcilePackageWithDirectory/changeToNonDefaultPackage/changeToNonDefaultPackage.test")
    public void testReconcilePackageWithDirectory_changeToNonDefaultPackage_ChangeToNonDefaultPackage() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/reconcilePackageWithDirectory/changeToNonDefaultPackage/changeToNonDefaultPackage.test");
        doTest(fileName);
    }

    @TestMetadata("reconcilePackageWithDirectory/innerClass/innerClass.test")
    public void testReconcilePackageWithDirectory_innerClass_InnerClass() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/reconcilePackageWithDirectory/innerClass/innerClass.test");
        doTest(fileName);
    }

    @TestMetadata("reconcilePackageWithDirectory/packageMatchesDirectory/packageMatchesDirectory.test")
    public void testReconcilePackageWithDirectory_packageMatchesDirectory_PackageMatchesDirectory() throws Exception {
        String fileName = KotlinTestUtils.navigationMetadata("idea/testData/multiFileIntentions/reconcilePackageWithDirectory/packageMatchesDirectory/packageMatchesDirectory.test");
        doTest(fileName);
    }
}
