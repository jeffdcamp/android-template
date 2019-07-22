pipeline {
    agent any

    environment {
        APP_MODULE_NAME = 'android-template'
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
                            androidLint()
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
            }
        }
        stage("Alpha") {
            when {
                branch 'master'
            }

            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleAlpha'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/alpha/${APP_MODULE_NAME}-alpha.apk"
                        }
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
                            androidLint()
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Deploy to Play Store Alpha") {
//                    steps {
//                        sh './gradlew publishAlphaApk'
//                    }
                    steps {
                        sh './gradlew publishAlphaBundle'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/alpha/${APP_MODULE_NAME}-alpha.aab"
                        }
                    }
                }
            }
        }
        stage("Preview") {
            when {
                branch 'preview'
            }

            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assemblePreview'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/preview/${APP_MODULE_NAME}-preview.apk"
                        }
                    }
                }
                stage("Test") {
                    steps {
                        sh './gradlew testPreviewUnitTest'
                    }
                    post {
                        always {
                            junit '**/build/test-results/**/TEST-*.xml'
                        }
                    }
                }
                stage("Lint") {
                    steps {
                        sh './gradlew lintPreview'
                    }
                    post {
                        always {
                            androidLint()
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Deploy to Play Store Preview") {
//                    steps {
//                        sh './gradlew publishPreviewApk'
//                    }
                    steps {
                        sh './gradlew publishPreviewBundle'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/preview/${APP_MODULE_NAME}-preview.aab"
                        }
                    }
                }
            }
        }
        stage("Beta") {
            when {
                branch 'beta'
            }
            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleBeta'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/beta/${APP_MODULE_NAME}-beta.apk"
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
                            androidLint()
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Deploy to Play Store") {
//                    steps {
//                        sh './gradlew publishBetaApk'
//                    }
                    steps {
                        sh './gradlew publishBetaBundle'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/beta/${APP_MODULE_NAME}-beta.aab"
                        }
                    }
                }
            }
        }
        stage("Release") {
            when {
                branch 'release'
            }
            stages {
                stage("Build") {
                    steps {
                        sh './gradlew clean assembleRelease'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/apk/release/${APP_MODULE_NAME}-release.apk"
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
                            androidLint()
                            archiveArtifacts 'app/build/reports/*.html'
                        }
                    }
                }
                stage("Deploy to Play Store") {
//                    steps {
//                        sh './gradlew publishReleaseApk'
//                    }
                    steps {
                        sh './gradlew publishReleaseBundle'
                    }
                    post {
                        always {
                            archiveArtifacts "app/build/outputs/bundle/release/${APP_MODULE_NAME}-release.aab"
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