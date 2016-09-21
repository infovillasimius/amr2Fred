/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amr2fred;


import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.VCARD;



/**
 *
 * @author anto
 */
public class Rdf {

    static String personURI    = "http://www.ontologydesignpatterns.org/ont/fred/domain.owl#";
    static String fullName     = "person_1";
    
    public Resource getResource(){



        // some definitions
        

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
        
        // create the resource
        Resource johnSmith = 
            model.createResource(personURI)
                 .addProperty(OWL.equivalentClass, fullName);
                
        
        
        model.write(System.out);
        
        return johnSmith;
    }
}
