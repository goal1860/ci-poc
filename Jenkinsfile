pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting....."'
        script {
          env.DEP_RC_OK=input(
            message: 'What is result of deployment?', id: 'DEP_RC_OK', ok: 'Pass',
            parameters:[choice(name: 'DEP_RC_OK', choices: 'pass\fail', description: 'What is result of deployment?')]
          )
          echo ${env.DEP_RC_OK}
        }
        
      }
    }
    stage('Deploy to RC') {
      steps {
        sh 'echo "Deploying to RC."'
      }
    }
  }
}