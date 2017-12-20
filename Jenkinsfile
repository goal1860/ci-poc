pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting...."'
      }
    }
    stage('Deploy to RC') {
      steps {
        sh 'echo "Deploying to RC."'
        sh 'echo "Done."'
      }
      post {
        success {
          script {
            env.DEP_RC = 'pass'
          }
          
          
        }
        
        failure {
          script {
            env.DEP_RC = 'fail'
          }
          
          
        }
        
      }
    }
    stage('Functional Test - CAPI') {
      when {
        expression {
          env.DEP_RC == 'pass'
        }
        
      }
      steps {
        script {
          env.AT=input(
            message: 'Do you want to CAPI test pass?', id: 'AT', ok: 'Submit',
            parameters:[choice(name: 'AT', choices: 'pass\nfail', description: 'Select pass the test will pass.')]
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

    stage('Deploy to Prelive') {
      steps {
        sh 'echo "Deploying to Prelive."'
        sh 'echo "Done."'
      }
      post {
        success {
          script {
            env.DEP_PRELIVE = 'pass'
          }

        }

        failure {
          script {
            env.DEP_PRELIVE = 'fail'
          }

        }

      }
    }

    stage('Deploy to canary boxes') {
      when {
        expression {
          env.DEP_PRELIVE == 'pass'
        }

      }
      steps {
        sh 'echo "Deploying to canary boxes."'
        sh 'echo "Done."'
        script {
          env.DEP_CANARY=input(
            message: 'Does canary verification pass?', id: 'DEP_CANARY', ok: 'Submit',
            parameters:[choice(name: 'DEP_CANARY', choices: 'pass\nfail', description: 'oes canary verification pass?')]
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

      }
    }

    stage('Deploy to Prod') {
      when {
        expression {
          env.DEP_CANARY == 'pass'
        }

      }
      steps {
        sh 'echo "Deploy to all production instances."'

      }
    }

  }
}
