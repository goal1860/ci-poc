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
          AT_RC=input(
            message: 'Do you want to CAPI test pass?', id: 'AT_RC', ok: 'Submit',
            parameters:[choice(name: 'AT_RC', choices: 'pass\nfail', description: 'Select pass the test will pass.')]
          )
          echo ("${AT_RC}")
        }
        sh ('/usr/bin/mvn clean test -Dresult=${AT_RC}'
      }
      post {
        always {
          junit '**/target/surefire-reports/**/*.xml'
          
        }
        
        success {
          script {
            env.AT_RC = 'pass'
          }
          
          
        }
        
        failure {
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
