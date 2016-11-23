# amr2Fred

Application amr2fred translates from AMR to RDF according the syntax of FRED (http://wit.istc.cnr.it/stlab-tools/fred/)



Tested Input 

(w / want-01 :polarity - :arg0 (b / boy) :arg1 (b2 / believe-01 :arg0 (g / girl) :arg1 b)) 				
The boy desires the girl to believe him

(f / fight-01 :prep-against (d / dog) :mode imperative) 												
Fight against the dog!

(r / recommend-01 :ARG1 (g / go-02 :ARG0 (b / boy)) :polarity -) 										
The boy shouldn't go.

(p / possible-01 :ARG1 (r / rain-01)) 																	
It may rain.

(y / yippee :mode expressive)																			
yippee!

(b / boy :mod (t / that)) 																				
that boy

(b / boy :quant 4 :ARG0-of (m / make-01 :ARG1 (p / pie)))  												
four boys making pies

(k / know-01 :ARG0 (w / we) :ARG1 (k2 / knife :location (d / drawer))) 
(t / thing :ARG1-of (p / propose-01))
(o / organization :ARG0-of (m / make-01 :ARG1 (c / chip)))
(p / person :ARG0-of (i / invest-01))


		
He was charged with public intoxication and escaping arrest.
(c / charge-05
   :ARG1 (h / he)
   :ARG2 (a / and
            :op1 (i / intoxicate-01
                    :ARG1 h
                    :location (p / public))
            :op2 (r / escape-01
                    :ARG0 h
                    :ARG1 (a2 / arrest-01
                              :ARG1 h))))
                              
                              
                              
                              

(s / say-01
      :ARG0 (s2 / service
            :mod (e / emergency)
            :location (c / city :wiki "London"
                  :name (n / name :op1 "London")))
      :ARG1 (s3 / send-01
            :ARG1 (p / person :quant 11)
            :ARG2 (h / hospital)
            :mod (a / altogether)
            :purpose (t / treat-03
                  :ARG1 p
                  :ARG2 (w / wound-01
                        :ARG1 p
                        :mod (m / minor)))))
