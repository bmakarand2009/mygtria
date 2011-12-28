package mygtria

class PersonTag implements  Serializable{

    static constraints = {
    }

    static mapping = {
           table('person_tag')
           version(false)
           id composite:['person','tag']
    }



    Person person
    Tag tag
}
