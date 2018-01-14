$(document).ready(function () {

    var fred;
    checkFred();
    riempi();
    assegna();
    chekRes();

    $('#prid').hide();
    $('.Amr2fred').hide();
    $('#fredresult').hide();

    $("#fred").change(function (event) {
        checkFred();
    });

    $('#amr2fred').click(function (event) {
        event.preventDefault();
        var urlo = "amr2fred";
        var urlo1 = "fred";
        var urlo2 = "compare";
        if ($('#rid')[0].checked === true) {
            urlo += "&?rid_err=ON";
        }

        if ($('#res')[0].checked === true) {
            urlo += "&?objAsRes";
        }


        var urlControl = urlo;
        var output = $('select#outMode option:selected').attr('value');
        var amr = $('#amr').val();
        var text = $('#text').val();
        urlControl += "&?amr=" + amr;

        switch (output) {
            case "0":
                urlo1 += "&?format=png" + "&?text=" + text;
                urlo += "&?format=png" + "&?amr=" + amr;
                break;
            case "1":
                urlo += "&?amr=" + amr;
                urlo1 += "&?format=RDF_XML" + "&?text=" + text;
                break;
            case "2":
                urlo += "&?format=RDF_XML_ABBREV" + "&?amr=" + amr;
                urlo1 += "&?format=RDF_XML" + "&?text=" + text;
                break;
            case"3":
                urlo += "&?format=N_TRIPLES" + "&?amr=" + amr;
                urlo1 += "&?format=N_TRIPLES" + "&?text=" + text;
                break;
            case "4":
                urlo += "&?format=TURTLE" + "&?amr=" + amr;
                urlo1 += "&?format=TURTLE" + "&?text=" + text;
                break;
            default:
                urlo += "&?proMode=on" + "&?amr=" + amr;
                urlo1 += "&?format=image/png" + "&?text=" + text;
        }

        var client = new HttpClient();
        var errors = false;
        var errResponse = "";
        $('.Amr2fred').show();
        $('#amr2fredresult').html("<img id=\"imgamr2fred\" src=\"loading.gif\" alt=\"Loading\" >");

        client.get(encodeURI(urlControl), function (response) {


            errResponse = response.toString();

            if (response.startsWith("Warning")) {
                $('#prid').show();
                errors = true;

            } else {
                $('#rid')[0].checked = false;
                $('#prid').hide();
                errors = false;
            }

            if (response.startsWith("Warning") || response.startsWith("No text") || response.startsWith("Sintax error")) {
                errors = true;
            } else {
                errors = false;
            }
        });

        client.get(encodeURI(urlo), function (response) {

            if (errors) {
                $('#amr2fredresult').html("<textarea id=\"tofred\" name=\"tofred\" rows=\"10\" cols=\"200\"></textarea>");
                $('#tofred').val(errResponse);
            } else {

                if (output > 0) {

                    $('#amr2fredresult').html("<textarea id=\"tofred\" name=\"tofred\" rows=\"10\" cols=\"200\"></textarea>");
                    $('#tofred').val(response);

                } else {
                    var uri = encodeURI(urlo);
                    $('#amr2fredresult').html("<img id=\"imgamr2fred\" src=" + uri + " alt=\"Amr2Fred's result\" >");
                }

            }


        });
        /**
         * if fred checkbox is selected get FRED response
         */
        if (fred) {
            $('#fredresult').show();
            $('#fredresult').html("<p class=\"left\"><img class=\"loghini\" src=\"ktools_logo_short.png\"> Fred result...</p><img class=\"imgfred\" src=\"loading.gif\" alt=\"Loading\" >");
            client.get(encodeURI(urlo1), function (response) {

                if (output > 0) {
                    $('#fredresult').html("<p class=\"left\"><img class=\"loghini\" src=\"ktools_logo_short.png\"> Fred result...</p><textarea id=\"tofred2\" name=\"tofred2\" rows=\"10\" cols=\"200\"></textarea>");
                    $('#tofred2').val(response);
                } else {
                    var uri = encodeURI(urlo1);
                    
                    $('#fredresult').html("<p class=\"left\"><img class=\"loghini\" src=\"ktools_logo_short.png\"> Fred result...</p><img class=\"imgfred\" src=" + uri + " alt=\"Fred's result\" >");
                }
            });

            $('#compareresult').show();
            $('#compareresult').html("<p class=\"left\"><img class=\"loghini\" src=\"Logo_UniCa.png\"> Comparing results...</p><img class=\"imgfred\" src=\"loading.gif\" alt=\"Loading\" >");
            urlo2 += "&?amr=" + amr + "&?sentence=" + text;
            var urlo3=encodeURI(urlo2+"&?commons=DIGRAPH");
            
            client.get(encodeURI(urlo2), function (response) {
                
                $('#compareresult').html("<p class=\"left\"><img class=\"loghini\" src=\"Logo_UniCa.png\"> Comparing results...</p><textarea id=\"compareresult2\" name=\"compareresult2\" rows=\"12\" cols=\"200\" readonly=\"readonly\"></textarea>" +
                                            "<p class=\"left\"><img class=\"loghini\" src=\"Logo_UniCa.png\"> Common Triples</p><img class=\"imgfred\" src=" + urlo3 + " alt=\"Commons triples\" >");
                $('#compareresult2').val(response);

            });
        }
    });

    /**
     * output mode on change action 
     */
    $('#outMode').change(function (event) {
        chekRes();
    });

    /**
     * checkbox for select if the objects are resources or strings is shown only in textual modes 
     * @returns {undefined}
     */
    function chekRes() {
        var output = $('select#outMode option:selected').attr('value');
        if (output > 0) {
            $('#pres').fadeTo(0, 1);
        } else {
            $('#pres').fadeTo(0, 0);
        }
    }


    /**
     * Example on change action
     */
    $("#lista").change(function (event) {
        assegna();
        $('#fredresult').html("");
        $('#amr2fredresult').html("");
        $('.Amr2fred').hide();
        $('#fredresult').hide();
        $('#compareresult').html("");
        $('#compareresult').hide();
    });

    /**
     * Example on click action 
     */
    $("#lista").click(function (event) {
        assegna();
        $('#fredresult').html("");
        $('#amr2fredresult').html("");
        $('.Amr2fred').hide();
        $('#fredresult').hide();
        $('#compareresult').html("");
        $('#compareresult').hide();
    });

    /**
     * Write the selected example to amr and text textareas
     * @returns {undefined}
     */
    function assegna() {
        var id = $('select#lista option:selected').attr('value');
        var t = $('select#lista option:selected').attr('title');
        if (id !== undefined && t !== undefined) {
            $('#amr').val(id);
            $('#text').val(t);
        }
    }

    /**
     * load list of examples
     * @returns {undefined}
     */
    function riempi() {
        var client = new HttpClient();
        client.get("lista.txt", function (response) {
            $("#lista").html(response);
        });
    }

    /**
     * check fred checkbox showing and hiding result divs
     * @returns {undefined}
     */
    function checkFred() {
        if ($('#fred')[0].checked === true) {
            fred = true;
            $("div.Fred").show();
        } else {
            fred = false;
            $("div.Fred").hide();
        }
        $('#fredresult').html("");
        $('#amr2fredresult').html("");
        $('.Amr2fred').hide();
        $('#fredresult').hide();
        $('#compareresult').html("");
        $('#compareresult').hide();
    }


    $(".tooltip").tooltip();
    $('#text').tooltip();
    $('#amr').tooltip();
    $('#compareresult').tooltip();

});

/** Get results from server **/
var HttpClient = function () {
    this.get = function (aUrl, aCallback) {
        var anHttpRequest = new XMLHttpRequest();
        anHttpRequest.onreadystatechange = function () {
            if (anHttpRequest.readyState === 4 && anHttpRequest.status === 200)
                aCallback(anHttpRequest.responseText);
        };
        anHttpRequest.open("GET", aUrl, true);
        anHttpRequest.send(null);
    };
};



