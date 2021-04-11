// ==== Jenkins build server setup ====
// 1. Install Jenkins
// 2. Install Jenkins Blue Ocean plugin (Pipeline builds)
// 3. Install Java in Jenkins
//     - Get tar.gz link from https://adoptopenjdk.net/ (Example: https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.10%2B9/OpenJDK11U-jdk_x64_linux_hotspot_11.0.10_9.tar.gz)
//     - Manage Jenkins > Global Tool Configuration > JDK > JDK installations...
//     - Add JDK
//         - Name: Java 11
//         - Add Installer > Extract *.zip/*.tar.gz (remove default Oracle installation)
//         - Label: master
//         - Download Url: <use above url>
//         - Subdirectory of extracted archive: <open the tar.gz file and use the root folder name here>
//     - Save
// 4. Install Android SDK
//     - Download latest Android SDK (https://developer.android.com/studio#command-tools) (Example: https://dl.google.com/android/repository/commandlinetools-linux-6858069_latest.zip)
//     - mkdir /opt/Android
//     - unzip downloaded file...
//     - move contents into /opt/Android/cmdline-tools/latest
//     - cd /opt/Android/cmdline-tools/latest/bin
//     - ./sdkmanager --update
//     - ./sdkmanager --licenses
//     - grant rights for jenkins user to /opt/Android dir
// 5. Configure Android Environment Variables in Jenkins
//     - Manage Jenkins>Configure System. Under Global Settings
//     - ANDROID_HOME=/opt/Android
//     - Save
// 6. For Firebase App Distribution, install firebase CLI (curl -sL https://firebase.tools | bash)  (https://firebase.google.com/docs/cli#install-cli-mac-linux)
// 7. Add PipeLine for this project to Jenkins

pipeline {
    agent any

    environment {
        APP_ARCHIVE_NAME = 'app'
        APP_MODULE_NAME = 'android-template'
        CHANGELOG_CMD = 'git log --date=format:"%Y-%m-%d" --pretty="format: * %s% b (%an, %cd)" | head -n 10 > commit-changelog.txt'
        FIREBASE_GROUPS = 'mobile-dev-team, mobile-qa-team'
        FIREBASE_APP_DIST_CMD = "firebase appdistribution:distribute app/build/outputs/apk/\$APP_BUILD_TYPE/app-\$APP_BUILD_TYPE.apk --app \$FIREBASE_ID --release-notes-file commit-changelog.txt --groups \"\$FIREBASE_GROUPS\""
        GOOGLE_APPLICATION_CREDENTIALS = "${HOME}/google-service-accounts/${APP_MODULE_NAME}.json"
    }

    tools {
        jdk "Java 11"
    }

    options {
        // prevent multiple builds from running concurrently, that come from the same git branch
        disableConcurrentBuilds()
    }

    stages {
        stage("PR") {
            when {
                changeRequest target: 'master'
            }
            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleAlpha'
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testAlphaUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detekt'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
            }
        }
        stage("Alpha") {
            environment {
                APP_BUILD_TYPE = "alpha"
                FIREBASE_ID = '1:292666345594:android:21903887d2d5200065d3c3' // from your project google-services.json client_info
            }

            when {
                branch 'master'
            }

            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleAlpha bundleAlpha'
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testAlphaUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detekt'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
                stage("App Distribution") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh "${FIREBASE_APP_DIST_CMD}"
                    }
                }
                stage("Deploy to Play Store Alpha") {
                    steps {
                        sh './gradlew publishAlphaBundle --artifact-dir app/build/outputs/bundle/alpha'
                    }
                }
            }
        }
        stage("Beta") {
            environment {
                APP_BUILD_TYPE = "beta"
                FIREBASE_ID = '1:292666345594:android:79471cfe9138c223' // from your project google-services.json client_info
            }
            when {
                branch 'beta'
            }
            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleBeta bundleBeta'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/beta/${APP_ARCHIVE_NAME}-beta.apk"
                        }
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testBetaUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintBeta'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detekt'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
                stage("App Distribution") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh "${FIREBASE_APP_DIST_CMD}"
                    }
                }
                stage("Deploy to Play Store") {
                    steps {
                        sh "./gradlew publishBetaBundle --artifact-dir app/build/outputs/bundle/beta"
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/beta/${APP_ARCHIVE_NAME}-beta.aab"
                        }
                    }
                }
            }
        }
        stage("Release") {
            environment {
                APP_BUILD_TYPE = "release"
                FIREBASE_ID = '1:292666345594:android:79471cfe9138c223' // from your project google-services.json client_info
            }
            when {
                branch 'release'
            }
            stages {
                stage("Build") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh './gradlew clean assembleRelease bundleRelease appDistributionUploadRelease'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/release/${APP_ARCHIVE_NAME}-release.apk"
                        }
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testReleaseUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintRelease'
                    }
                    post {
                        always {
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Detekt") {
                    steps {
                        sh './gradlew downloadDetektConfig detekt'
                    }
                    post {
                        always {
                            archiveArtifacts '*/build/reports/detekt/*.html'
                        }
                    }
                }
                stage("App Distribution") {
                    steps {
                        sh "${CHANGELOG_CMD}"
                        sh "${FIREBASE_APP_DIST_CMD}"
                    }
                }
                stage("Deploy to Play Store") {
                    steps {
                        sh "./gradlew publishReleaseBundle --artifact-dir app/build/outputs/bundle/release"
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/release/${APP_ARCHIVE_NAME}-release.aab"
                        }
                    }
                }
            }
        }
        stage("Increment Version Code") {
            when {
                anyOf { branch 'master'; branch 'beta'; branch 'release' }
            }
            steps {
                sh './gradlew incrementVersionCode'
            }
        }
    }

    post {
        failure {
            mail(to: 'jeffdcamp@gmail.com',
                    subject: "Job '${env.JOB_NAME}' (${env.BUILD_NUMBER}) has failed",
                    body: "Please go to ${env.BUILD_URL}.")

            // Notify build breaker
            step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])])
        }
    }
}