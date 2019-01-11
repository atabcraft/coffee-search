# Demo Spring + Angular 7 application

This is a demo application. Technologies used here demonstrate my working proficiency as a full-stack developer.

Short list of goals of this demo is described on dashboard. Application is focussed 
on viewing a predefined list of coffee items. This list can be searched using search bar on efficient way.
Idea is to demonstrate full-text search capabilities in PostgreSQL 
and efficient search approach using search engine noSQL database - Elasticsearch.
When you look at the graphical interface you should see a decent design and good user experiance ( good UI + UX ).

This application is secure , ordering a coffee can't be made by just anyone.
Spring Security is used with JWT token authentification.

Also all stuff that is being made in application is being tracked ( Entities are audited, user activities are being tracked ).

There are few types of users in application:

- ADMIN
- ENGINEER
- BAD_MANAGER

They all have their privileges. Some can drink coffee while others can't.

## Basic information and small introduction

Information about fronted and backend subprojects can be found by clicking the direcory of subprojects.

## Resources
- https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-auditing-part-one/
- https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-auditing-part-two/
- https://docs.spring.io/spring-data/elasticsearch/docs/3.2.0.M1/reference/html/#reference
- https://stackoverflow.com/questions/19930152/what-is-persistence-context
- https://stackoverflow.com/questions/44756872/spring-core-default-bean-destroy-method
- https://github.com/izeye/spring-boot-throwaway-branches/commit/874ccba09189d6ef897bc430c43b6e3705404399
