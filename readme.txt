Issue :
Grails Nabble link : http://grails.1312388.n4.nabble.com/GORM-Criteria-Query-throwing-errors-tc4236273.html#a4239075

I am getting error when I execute the following hibernate criteria

def pList1 = PersonTag.createCriteria().list{
    person{
        eq('client', c1)
    }

}

Version of Grails is 1.3.7
Datasource.groovy has log.sql true...


How to Reproduce it
==================

Go to grails console and paste the following

import mygtria.*
Client c1 = new Client(name:"acli").save(failOnError:true, flush:true)
Person p1 = new Person(firstName:"john",client:c1).save(failOnError:true, flush:true)
Tag t1 = new Tag(name:"atestTag",client:c1).save(failOnError:true, flush:true)
PersonTag pt = new PersonTag(person:p1,tag:t1).save(failOnError:true, flush:true)



println PersonTag.list()
def pList1 = PersonTag.createCriteria().list{
    person{
        eq('client', c1)
    }

}

println pList1


Following the Console Output
============================

groovy> import mygtria.* 
groovy> Client c1 = new Client(name:"acli").save(failOnError:true, flush:true) 
groovy> Person p1 = new Person(firstName:"john",client:c1).save(failOnError:true, flush:true) 
groovy> Tag t1 = new Tag(name:"atestTag",client:c1).save(failOnError:true, flush:true) 
groovy> PersonTag pt = new PersonTag(person:p1,tag:t1).save(failOnError:true, flush:true) 
groovy> println PersonTag.list() 
groovy> def pList1 = PersonTag.createCriteria().list{ 
groovy>     person{ 
groovy>         eq('client', c1) 
groovy>     } 
groovy> } 
groovy> println pList1 
 
Hibernate: 
    insert 
    into
        client
        (id, version, name) 
    values
        (null, ?, ?)
Hibernate: 
    call identity()
Hibernate: 
    insert 
    into
        person
        (id, version, client_id, first_name) 
    values
        (null, ?, ?, ?)
Hibernate: 
    call identity()
Hibernate: 
    select
        this_.id as id2_0_,
        this_.version as version2_0_,
        this_.client_id as client3_2_0_,
        this_.name as name2_0_ 
    from
        tag this_ 
    where
        this_.name=? 
        and this_.client_id=?
Hibernate: 
    insert 
    into
        tag
        (id, version, client_id, name) 
    values
        (null, ?, ?, ?)
Hibernate: 
    call identity()
Hibernate: 
    select
        persontag_.person_id,
        persontag_.tag_id 
    from
        person_tag persontag_ 
    where
        persontag_.person_id=? 
        and persontag_.tag_id=?
Hibernate: 
    insert 
    into
        person_tag
        (person_id, tag_id) 
    values
        (?, ?)
Hibernate: 
    select
        this_.person_id as person1_3_0_,
        this_.tag_id as tag2_3_0_ 
    from
        person_tag this_
[mygtria.PersonTag : null]
Hibernate: 
    select
        this_.person_id as person1_3_0_,
        this_.tag_id as tag2_3_0_ 
    from
        person_tag this_ 
    where
        (
            person_ali1_.client_id=?
        )
Exception thrown

org.hibernate.exception.SQLGrammarException: could not execute query
	at org.hibernate.exception.SQLStateConverter.convert(SQLStateConverter.java:90)
	at org.hibernate.exception.JDBCExceptionHelper.convert(JDBCExceptionHelper.java:66)
	at org.hibernate.loader.Loader.doList(Loader.java:2231)
	at org.hibernate.loader.Loader.listIgnoreQueryCache(Loader.java:2125)
	at org.hibernate.loader.Loader.list(Loader.java:2120)
	at org.hibernate.loader.criteria.CriteriaLoader.list(CriteriaLoader.java:118)
	at org.hibernate.impl.SessionImpl.list(SessionImpl.java:1596)
	at org.hibernate.impl.CriteriaImpl.list(CriteriaImpl.java:306)
	at grails.orm.HibernateCriteriaBuilder.invokeMethod(HibernateCriteriaBuilder.java:1163)
	at ConsoleScript2.run(ConsoleScript2:10)
Caused by: java.sql.SQLException: Column not found: PERSON_ALI1_.CLIENT_ID in statement [select this_.person_id as person1_3_0_, this_.tag_id as tag2_3_0_ from person_tag this_ where (person_ali1_.client_id=?)]
	at org.hsqldb.jdbc.Util.throwError(Unknown Source)
	at org.hsqldb.jdbc.jdbcPreparedStatement.<init>(Unknown Source)
	at org.hsqldb.jdbc.jdbcConnection.prepareStatement(Unknown Source)
	at org.apache.commons.dbcp.DelegatingConnection.prepareStatement(DelegatingConnection.java:281)
	at org.apache.commons.dbcp.PoolingDataSource$PoolGuardConnectionWrapper.prepareStatement(PoolingDataSource.java:313)
	at org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy$TransactionAwareInvocationHandler.invoke(TransactionAwareDataSourceProxy.java:239)
	at $Proxy8.prepareStatement(Unknown Source)
	at org.hibernate.jdbc.AbstractBatcher.getPreparedStatement(AbstractBatcher.java:534)
	at org.hibernate.jdbc.AbstractBatcher.getPreparedStatement(AbstractBatcher.java:452)
	at org.hibernate.jdbc.AbstractBatcher.prepareQueryStatement(AbstractBatcher.java:161)
	at org.hibernate.loader.Loader.prepareQueryStatement(Loader.java:1573)
	at org.hibernate.loader.Loader.doQuery(Loader.java:696)
	at org.hibernate.loader.Loader.doQueryAndInitializeNonLazyCollections(Loader.java:259)
	at org.hibernate.loader.Loader.doList(Loader.java:2228)
	... 7 more



############
WORKAROUND
Currently I have the workaorund for it 
