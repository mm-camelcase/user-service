docker build -t test-ssh-image .
docker tag test-ssh-image camelcasemm/che-devenv-image:2.0.0
docker push camelcasemm/che-devenv-image:2.0.0



# oc login
DEV_WORKSPACE_NAME="user-api-example"
DEV_POD_NAME=$(oc get pods -o json | jq -r --arg prefix "$DEV_WORKSPACE_NAME" '.items[] | select(.metadata.labels["controller.devfile.io/devworkspace_name"] | startswith($prefix)) | .metadata.name')
oc port-forward "$DEV_POD_NAME" 3000:3000