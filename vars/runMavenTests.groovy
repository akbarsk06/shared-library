def call(boolean runTestsFlag) {
    if (!runTestsFlag) {
        echo "RUN_TESTS parameter is false. Skipping tests."
        return
    }

    if ((env.BUILD_NUMBER as Integer) <= 2 || currentBuild.previousBuild?.result == 'FAILURE' ||
        (currentBuild.changeSets.any { cs -> cs.items.any { it.affectedFiles.any { f -> 
            f.path.startsWith('src/') || f.path.endsWith('.java') || f.path == 'pom.xml' } } })) {
        echo "Running Maven tests..."
        sh 'mvn -B test'
    } else {
        echo "Skipping tests due to no relevant changes and build number > 2"
    }
}
