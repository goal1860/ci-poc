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
        sh '''mvn_result=$?
        echo "Return result: ${mvn_result}"
exit ${mvn_result}'''
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
        }
        stage('Run Orion Tests') {
          steps {
            sh 'echo "Running Orion tests."'
          }
        }
      }
    }
  }
}
