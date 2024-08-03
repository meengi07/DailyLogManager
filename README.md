# RestScheduler

## Infomation
API 엔드포인트로 @Scheduled 어노테이션의 등록된 스케줄을 id로 식별하고,  on/off 하거나, 스케줄러의 시간을 변경하는 라이브러리


## What is RestScheduler?
<b>RestScheduler</b> 는 스프링 프레임워크에서 제공하는 @Scheduled 어노테이션을 이용하여 스케줄러를 등록하고, 이를 API 엔드포인트로 제어할 수 있는 라이브러리입니다. RestScheduler는 스케줄러의 id를 통해 스케줄러를 식별하고, 해당 스케줄러를 on/off 하거나, 스케줄러의 시간을 변경할 수 있습니다.

## How to use RestScheduler?
1. RestScheduler 라이브러리를 프로젝트에 추가합니다.
2. @EnableScheduling 어노테이션이 선언되어 있어야합니다. 
3. RestScheduler를 사용할 메서드에 @RestScheduled 어노테이션을 사용합니다.
4. API 를 사용한 스케줄러 각 엔드포인트를 사용하여 컨트롤 할 수 있습니다.