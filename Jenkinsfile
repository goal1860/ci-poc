pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting....."'
        script {
            input(message: 'What is result of deployment?', id: 'DEP_RC_OK', ok: 'Pass',
                parameters:[
                    [$class: 'TextParameterDefinition', defaultValue: 'uat', description: 'Environment', name: 'env']
                ]
            )
        }
        echo "${env.DEP_RC_OK}"
      }
    }
    stage('Deploy to RC') {
      steps {
        sh 'echo "Deploying to RC."'
      }
    }
  }
}
