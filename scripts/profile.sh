#!/usr/bin/env bash
# 스크립트를 실행시켜줄 프로그램의 경로를 지정하는 역할

# profile.sh 는 앞선 4개 스크립트 파일에서 공용으로 사용할 'profile'과 포트 체크 로직이다.

function find_idle_profile()
{
    # 현재 Nginx가 바라보고 있는 스프링 부트가 정상적으로 수행 중인지 확인
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

    # [ -ge ] : 값1  >= 값2
    if [ ${RESPONSE_CODE} -ge 400 ] # 400 보다 크면 에러(40x, 50x 에러 모두  포함)

    then
        CURRENT_PROFILE=real2
    else
        CURRENT_PROFILE=$(curl -s http://localhost/profile)
    fi

    if [ ${CURRENT_PROFILE} == real1 ]
    then
      IDLE_PROFILE=real2
    else
      IDLE_PROFILE=real1
    fi

    echo "${IDLE_PROFILE}"
}

# 쉬고 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}