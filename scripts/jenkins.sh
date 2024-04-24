API_TOKEN=$1
URL=$2
JOB=$3
JOB_TOKEN=$4

curl -X POST curl -I -u auto:${API_TOKEN} ${URL}/job/${JOB}/build?token=${JOB_TOKEN}