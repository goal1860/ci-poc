pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting...."'
      }
    }

    stage('Deploy to RC canary') {

      steps {
        sh 'echo "Deploying to RC canary boxes."'
        sh 'echo "Done."'

      }
    }

    stage('Verify RC canary') {

        steps {
            sh 'echo "Running smoke testing RC canary."'
            sh 'echo "Smoke testing completed."'
            sh 'echo "Monitoring RC canary."'
            script {
              env.DEP_RC_CANARY=input(
                message: 'Does canary verification pass?', id: 'DEP_RC_CANARY', ok: 'Submit',
                parameters:[choice(name: 'DEP_RC_CANARY', choices: 'pass\nfail', description: 'Does canary verification pass?')]
              )
              echo ("${env.DEP_RC_CANARY}")
            }
        }
    }
    stage('Roll back RC canary') {
      when {
        expression {
          env.DEP_RC_CANARY == 'fail'
        }

      }
      steps {
        sh 'echo "Rolling back the release from RC canary."'
        sh 'exit 1'

      }
    }

    stage('Promote release to remaining RC') {
      when {
        expression {
          env.DEP_RC_CANARY == 'pass'
        }

      }
      steps {
        sh 'echo "Promoting release to remaining RC."'
        sh 'echo "Done."'
      }
    }

    stage('Functional Test - CAPI') {
      steps {
        script {
          env.AT=input(
            message: 'Do you want to CAPI test pass for demo?', id: 'AT', ok: 'Submit',
            parameters:[choice(name: 'AT', choices: 'pass\nfail', description: 'Select Pass then the tests will pass.')]
          )
          echo ("${env.AT}")
        }
        
        sh "/usr/bin/mvn clean test -Dresult=${env.AT}"

      }
      post {
        always {
          junit '**/target/surefire-reports/**/*.xml'
        }

        success {
          echo "Test passed."
          script {
            env.AT_RC = 'pass'
          }


        }

        failure {
          echo "Test failed."
          script {
            env.AT_RC = 'fail'
          }

          archiveArtifacts '**/target/surefire-reports/*'
        }

      }
    }
    stage('Dependency Tests') {
      when {
        expression {
          env.AT_RC == 'pass'
        }

      }
      parallel {
        stage('Run Shop Tests') {
          steps {
            sh 'echo "Running shop tests."'
          }
          post {
            failure {
              echo "Shop Test failed."
              script {
                env.DT_RC = 'fail'
              }
            }
          }
        }
        stage('Run Orion Tests') {
          steps {
            sh 'echo "Running Orion tests."'
          }
          post {
            failure {
              echo "Orion Test failed."
              script {
                env.DT_RC = 'fail'
              }
            }
          }
        }
      }
    }

    stage('Performance Tests') {
      when {
        expression {
          env.DT_RC != 'fail'
        }

      }
      steps {
        sh 'echo "Running performance tests."'

      }
    }

    stage('Deploy to canary') {
      steps {
        sh 'echo "Deploying to canary."'
        sh 'echo "Done."'
      }
    }

    stage('Monitoring Canary') {
      steps {
        sh 'echo "Monitoring Canary"'
        script {
          env.DEP_CANARY=input(
            message: 'Does canary verification pass?', id: 'DEP_CANARY', ok: 'Submit',
            parameters:[choice(name: 'DEP_CANARY', choices: 'pass\nfail', description: 'Does canary verification pass?')]
          )
          echo ("${env.DEP_CANARY}")
        }
      }
    }

    stage('Roll back') {
      when {
        expression {
          env.DEP_CANARY == 'fail'
        }

      }
      steps {
        sh 'echo "Rolling back the release from canary box."'
        sh 'exit 1'
      }
    }

    stage('Promote to Prod remaining') {
      steps {
        sh 'echo "Deploy to all production instances."'

      }
    }

    stage('Smoke Testing') {
      steps {
        sh 'echo "Running smoke testing."'
        sh 'echo "Done"'
        script {
          env.FINAL=input(
            message: 'What is final decision?', id: 'FINAL', ok: 'Submit',
            parameters:[choice(name: 'FINAL', choices: 'yes\nno', description: 'Finalise the release?')]
          )
          echo ("${env.FINAL}")
        }
      }
    }

    stage('Roll back') {
      when {
        expression {
          env.final == 'no'
        }

      }
      steps {
        sh 'echo "Rolling back the release from Prod based on final decision."'
        sh 'exit 1'
      }
    }

    stage('Completion') {
      steps {
        sh 'echo "Merging branch."'
        sh 'echo "Update Jira status."'
      }
    }
  }
}
