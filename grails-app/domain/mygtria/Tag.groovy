package mygtria

class Tag {


    static constraints = {
        name(unique: 'client')
    }

    Client client
    String name;

    /*
    import mygtria.*
Client c1 = new Client(name:"acli").save(failOnError:true, flush:true)
Person p1 = new Person(name:"john",client:c1).save(failOnError:true, flush:true)
Tag t1 = new Tag(name:"atestTag").save(failOnError:true, flush:true)
PersonTag pt = new PersonTag(person:p1,tag:t1).save(failOnError:true, flush:true)



println PersonTag.list()
def pList1 = PersonTag.createCriteria().list{
    person{
        eq('client', c1)
    }

}

println pList1
     */
}
