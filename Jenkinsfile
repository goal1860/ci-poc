pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting....."'
      }
    }
    stage('Deploy to RC') {
      steps {
        sh 'echo "Deploying to RC."'
        script {
          env.DEP_RC_OK=input(
            message: 'What is result of deployment?', id: 'DEP_RC_OK', ok: 'Submit',
            parameters:[choice(name: 'DEP_RC_OK', choices: 'pass\nfail', description: 'What is result of deployment?')]
          )
          echo ("${env.DEP_RC_OK}")
        }
        
      }
    }
    stage('Functional Test - CAPI') {
      when {
        expression {
          env.DEP_RC_OK == 'pass'
        }
        
      }
      steps {
        sh '/usr/bin/mvn clean test'
      }
    }
  }
}
