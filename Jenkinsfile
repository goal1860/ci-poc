pipeline {
  agent any
  stages {
    stage('Init') {
      steps {
        sh 'echo "Starting..."'
        script {
            env.DEP_RC_OK = input(message: 'What is result of deployment?', id: 'rc_dep', ok: 'Pass')
        }
        echo "${env.DEP_RC_OK}"
      }
    }
  }
}
