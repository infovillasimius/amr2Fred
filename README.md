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
                        

New tested input

The boy desires the girl to believe him. -> (w / want-01 :ARG0 (b / boy) :ARG1 (b2 / believe-01 :ARG0 (g / girl) :ARG1 b))

The girl adjusted the machine. -> (a / adjust-01 :ARG0 (b / girl) :ARG1 (m / machine))

The killing happened yesterday. -> (k / kill-01 :time (y / yesterday))

The boy and the girl -> (a / and :op1 (b / boy) :op2 (g / girl))

His boat -> (b / boat :poss (h / he))

The boy must not go. -> (o / obligate-01 :ARG2 (g / go-02 :ARG0 (b / boy) :polarity -))

The boy may not go. -> (p / permit-01 :ARG1 (g / go-02 :ARG0 (b / boy)) :polarity -)

The marble is white. -> (w / white-03 :ARG1 (m / marble))

The boy sees that the marble is white. -> (s / see-01 :ARG0 (b / boy) :ARG1 (w / white-03 :ARG1 (m / marble)))

The boy sees the white marble. -> (s / see-01 :ARG0 (b / boy) :ARG1 (m / marble :ARG1-of (w / white-03)))

The boy saw the girl who wanted him. -> (s / see-01 :ARG0 (b / boy) :ARG1 (g / girl :ARG0-of (w / want-01 :ARG1 b)))

The boy is wanted by the girl he saw. -> (w / want-01 :ARG0 (g / girl :ARG1-of (s / see-01 :ARG0 (b / boy))) :ARG1 b)

The regulatory documents were changed. -> (c / change-01 :ARG1 (d / document :instrument-of (r / regulate-01)))

The boy wants to believe. -> (w / want-01 :ARG0 (b / boy) :ARG1 (b2 / believe-01 :ARG0 b))

The boy believes. -> (b / believe-01 :ARG0 (b / boy))

He described the mission as a failure. -> (d / describe-01 :ARG0 (h / he) :ARG1 (m / mission) :ARG2 (f / failure))

The band experienced a comeback -> (c / comeback-00 :ARG0 (b / band))

The boy can go. -> (p / possible-01 :ARG1 (g / go-02 :ARG0 (b / boy)))

The boy must go. -> (o / obligate-01 :ARG2 (g / go-02 :ARG0 (b / boy)))

The boy may go. -> (o / permit-01 :ARG1 (g / go-02 :ARG0 (b / boy)))

It may rain. -> (p / possible-01 :ARG1 (r / rain-01))

The boy should go. -> (r / recommend-01 :ARG1 (g / go-02 :ARG0 (b / boy)))

The boy is likely to go. -> (l / likely-01 :ARG1 (g / go-02 :ARG0 (b / boy)))

The boy prefers to go. -> (p / prefer-01 :ARG0 (b / boy) :ARG1 (g / go-02 :ARG0 b))

I am used to working. -> (u / use-01 :ARG0 (i / i) :ARG1 (w / work-01 :ARG0 i))

The boy doesn’t go. -> (g / go-02 :ARG0 (b / boy) :polarity -)

The boy can’t go. -> (p / possible-01 :ARG1  (g / go-02 :ARG0 (b / boy)) :polarity -)

It is possible for the boy not to go. -> (p / possible-01 :ARG1 (g / go-02 :ARG0 (b / boy) :polarity -))

The boy must not go. -> (p / obligate-01 :ARG2 (g / go-02 :ARG0 (b / boy)) :polarity -)

The boy thinks his team won’t win. -> (t / think-01 :ARG0 (b / boy) :ARG1 (w / win-01 :ARG0 (t2 / team :poss b) :polarity -))

I have no money. -> (h / have-01 :polarity - :ARG0 (i / i) :ARG1 (m / money))

The dress is inappropriate. -> (a / appropriate-02 :polarity - :ARG1 (d / dress))

The inappropriate dress -> (d / dress :ARG1-of (a / appropriate-02 :polarity -))

What did the girl find? -> (f / find-01 :ARG0 (g / girl) :ARG1 (a / amr-unknown)) -> "amr-unknown" dropped

I know who you saw. -> (k / know-01 :ARG0 (i / i) :ARG1 (p / person :ARG1-of (s / see-01 :ARG0 (y / you))))

Did the girl find the boy? -> (f / find-01 :ARG0 (g / girl) :ARG1 (b / boy) :mode interrogative) -> ":mode interrogative" dropped

Go! -> (g / go-02 :mode imperative :ARG0 (y / you)) -> ":mode imperative" dropped

Yippee! -> (y/yippee :mode expressive) -> ":mode expressive" dropped

Money-market account -> (a / account :mod (m / market :mod (m2 / money)))

He was charged with public intoxication and resisting arrest. -> (c / charge-05 :ARG1 (h / he) :ARG2 (a / and :op1 (i / 
intoxicate-01 :ARG1 h :location (p / public)) :op2 (r / resist-01 :ARG0 h :ARG1 (a2 / arrest-01 :ARG1 h))))

The hopeful girl -> (h / hopeful-03 :ARG1 (g / girl)) 

The marble is small. -> (s / small :domain (m / marble))

The man is a lawyer. -> (l / lawyer :domain (m / man))

The man who is a lawyer. -> (m / man :mod (l / lawyer))

Four boys making pies. -> (b / boy :quant 4 :ARG0-of (m / make-01 :ARG1 (p / pie)))

The boy destroyed the room. -> (d / destroy-01 :ARG0 (b / boy) :ARG1 (r / room))

The thing proposed. -> (t / thing :ARG1-of (p / propose-01))

The boy’s opinion. -> (t / thing :ARG1-of (o / opine-01 :ARG0 (b / boy))) 

Chip maker organization. -> (o / organization :ARG0-of (m / make-01 :ARG1 (c / chip)))

Nerdy investor. -> (p / person :ARG0-of (i / invest-01) :mod (n / nerd))

The boy is a hard worker. -> (w / work-01 :ARG0 (b / boy) :manner (h / hard-02)) 

The attractive man. -> (m / man :ARG0-of (a / attract-01))

The boy is responsible for the work. -> (r / responsible-03 :ARG0 (b / boy) :ARG1 (w / work)) 

The girl was saddened by the disaster. -> (s / sadden-01 :ARG1 (g / girl) :ARG2 (d / disaster))

The boy is acquainted with magic. -> (a / acquaint-01 :ARG1 (b / boy) :ARG2 (m / magic))

Boys want to please. -> (w / want-01 :ARG0 (b / boy) :ARG1 (p / please-01 :ARG0 b))

Girls are easy to please. -> (e / easy-05 :ARG1 (p / please-01 :ARG1 (g / girl)))

A sandwich that it is possible to eat. -> (s / sandwich :ARG1-of (e / eat-01 :ARG1-of (p / possible-01)))

I observed that the army moved quickly. -> (o / observe-01 :ARG0 (i / i) :ARG1  (m / move-01 :ARG0 (a / army) :manner (q / 
quick-02)))

He drove west, from Houston to Austin. -> (d / drive-01 :ARG0 (h / he) :direction (w / west) :source (c / city :wiki "Houston" 
:name (n / name :op1 "Houston")) :destination (c2 / city :wiki "Austin,_Texas" :name (n2 / name :op1 "Austin")))

I drove to Indianapolis on I-65. -> (d / drive-01 :ARG0 (i / i) :destination (c / city :wiki "Indianapolis" :name (n / name :op1 
"Indianapolis")) :path (r / road :wiki "Interstate_65" :name (n2 / name :op1 "I-65")))

The soldier hummed a tune for the girl as he walked with her to town. -> (s / hum-02 :ARG0 (s2 / soldier) :ARG1 (t2 / tune) 
:beneficiary (g / girl) :time (w / walk-01 :ARG0 g :accompanier s2 :destination (t / town)))



