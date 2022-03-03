# quartz-scheduler
API based scheduler based on redis and quartz. It supports schedule/unschedule/update jobs.

# reference
- Quartz: http://www.quartz-scheduler.org/documentation/
- quartz-redis-jobstore: https://github.com/jlinn/quartz-redis-jobstore

# Operation
- POST scheduler/{jobName}: to schedule/create job
- PATCH scheduler/{jobName}: to re-schedule job
- GET scheduler/{jobName}: to get the job information
- GET scheduler: list all job
- DELETE scheduler/{jobName}: delete job

