pipeline {
    agent any
    
    environment {
        maven_server = 'ubuntu@172.31.45.67'
        jenkins_server = 'ubuntu@172.31.41.72'
        docker_server = 'ubuntu@172.31.39.227'
        deploy = '/home/ubuntu/k8s/deployment.yaml'
        k8s_server = 'ubuntu@172.31.39.92'
    }
    
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, credentialsId: 'Git_creds', poll: false, url: 'https://github.com/AnkitSharmaDEVOPS/WebappMavenProjectMT.git'
            }
        }//Stage1closing
        stage('Send to MVN server') {
            steps {
               sshagent(['mvncreds']) {
                  sh " scp -o StrictHostKeyChecking=no -r * ${maven_server}:/home/ubuntu "
             }
            }
        }//Stage2Closing
        stage('Building the package') {
            steps {
                sshagent(['mvncreds']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${maven_server} '
                    mvn clean package
                    '
                    """
                }
            }
        }//stage3closing
        stage('Getting the Target Directory') {
            steps {
                sshagent(['mvncreds']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${maven_server} '
                    cp -r /home/ubuntu/target /tmp
                    scp -o StrictHostKeyChecking=no -r /tmp/target ${jenkins_server}:/home/ubuntu
                    '
                    """
                }
            }
        }//stage3closing
        stage('Target Transfer') {
            steps {
                sshagent(['Docker_creds']) {
                sh "scp -o StrictHostKeyChecking=no -r /home/ubuntu/target ${docker_server}:/home/ubuntu"
                 }
            }
        }//stage4closing
        stage('Docker File Build') {
            steps {
                sshagent(['Docker_creds']) {
                    sh """
                    ssh -o StrictHostKeyChecking=no ${env.docker_server} '
                        docker rmi -f \$(docker images -aq)
                        docker build -t image_${env.BUILD_NUMBER} -f docker .
                    '
                    """
                }
            }
        }//stage5closing        
        stage('Docker push') {
            steps {
                sshagent(['Docker_creds']) {
                    withCredentials([usernamePassword(credentialsId: 'Dockerhub_creds', passwordVariable: 'DHPass', usernameVariable: 'DHUser')]) {
                         sh """
                         ssh -o StrictHostKeyChecking=no ${docker_server} '
                           echo "$DHPass" | docker login -u $DHUser -password-stdin 
                           docker tag image_${env.BUILD_NUMBER} ankitsharmaa/image_${env.BUILD_NUMBER}
                           docker push ankitsharmaa/image_${env.BUILD_NUMBER}
                         '               
                        """
                    }
                }
            }
        }
        stage('Deploying on K8s') {
            steps {
                sshagent(['K8sServer']) {
                 sh """
                  ssh -o StrictHostKeyChecking=no ${env.k8s_server} '
                  sed -i "s|ankitsharmaa/image_[0-9]*|ankitsharmaa/image_${env.BUILD_NUMBER}|g" $deploy
                  kubectl apply -f $deploy 
                  '
                  """
                 }
            }
        
        }//stage6closing
    }//StagesClosing
}//PipelineClosing
