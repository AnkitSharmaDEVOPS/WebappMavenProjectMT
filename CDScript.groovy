pipeline {
    agent any

    environment {
        deploy = '/home/ubuntu/deployment.yaml'
        k8s_server = 'ubuntu@172.31.39.92'
    }

    stages {
        stage('Deploying on K8s') {
            steps {
                sshagent(['K8sServer']) {
                 sh """
                  ssh -o StrictHostKeyChecking=no  ${k8s_server}'
                  sed -i 's|ankitsharmaa/image_[0-9]*|ankitsharmaa/image_${env.BUILD_NUMBER}|g' $deploy
                  kubectl apply -f $deploy 
                  '
                  """
                 }
            }
        }
    }//stagesClosing
}//pipelineclsoing