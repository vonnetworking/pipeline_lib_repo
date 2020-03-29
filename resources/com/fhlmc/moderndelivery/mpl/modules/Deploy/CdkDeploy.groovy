sh "LOCAL_IMAGE_ID=`/usr/local/bin/docker images | grep springboot-test | awk '{print \$3}'` && /usr/local/bin/docker tag \$LOCAL_IMAGE_ID vonnetworking/springboot-test:app-dev-$app_version"

withCredentials([usernamePassword(credentialsId: 'av_dockerhub_id', usernameVariable: 'HUB_USER', passwordVariable: 'HUB_PASS')]) {
  sh "export PATH=$PATH:/usr/local/bin && /usr/local/bin/docker login -u \$HUB_USER -p \$HUB_PASS && /usr/local/bin/docker push vonnetworking/springboot-test"
sh "/usr/local/bin/docker rmi --force `/usr/local/bin/docker images | grep springboot-test | head -1 | awk '{ print \$3 }'`"
}

sh "cd cdk_ecs && cdk deploy"
