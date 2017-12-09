$(document).ready(function () {

    var host = $(location).attr('host');
    var baseURL = "http://" + host + "/amr2fred";
    $('#base').html("Base URI: " + baseURL);

    checkFred();

    $('#getIt').click(function (event) {
        event.preventDefault();
        var urlo = baseURL;
        var output = $('select#outMode option:selected').attr('value');

        if (output < 6) {

            if ($('#rid')[0].checked === true) {
                urlo += "&?rid_err=ON";
            }

            if ($('#res')[0].checked === true) {
                urlo += "&?objAsRes";
            }


            var amr = $('#amr').val();

            switch (output) {
                case "0":
                    urlo += "&?format=png" + "&?amr=" + amr;
                    break;
                case "1":
                    urlo += "&?format=RDF_XML" + "&?amr=" + amr;
                    break;
                case "2":
                    urlo += "&?format=RDF_XML_ABBREV" + "&?amr=" + amr;
                    break;
                case"3":
                    urlo += "&?format=N_TRIPLES" + "&?amr=" + amr;
                    break;
                case "4":
                    urlo += "&?format=TURTLE" + "&?amr=" + amr;
                    break;
                case "5":
                    urlo += "&?format=DIGRAPH" + "&?amr=" + amr;
                    break;
                default:
                    urlo += "&?proMode=on" + "&?amr=" + amr;
            }
        } else {
            var amr = $('#amr').val();
            urlo+="&?amr=" + amr+"&?sentence="+$('#text').val();
        }
        urlo = encodeURI(urlo);
        var href = '<a href="' + urlo + '" target="_blank">' + urlo + '</a>';
        $('#path').html(href);
    });

    $('body').change(function (event) {
        $('#path').html("");
        checkFred();
    });

    function checkFred() {
        var output = $('select#outMode option:selected').attr('value');
        if (output == 6) {
            baseURL = "http://" + host + "/compare";
            $('#parameters').html("<thead><tr><th>Parameter</th><th>Explanation</th></tr></thead>" +
                    "<tbody><tr><td>&?amr=(x)</td><td>(x) is the AMR encoded with UTF-8</td></tr>" +
                    "<tr><td>&?sentence=(s)</td><td>(s) is the text to send to FRED, encoded with UTF-8 )</td></tr>" +
                    "</tbody>" );
            fred = true;
            $("div.Fred").show();
            if ($('#text').val() == "" && $('#amr').val()=="(b / boy :quant 4 :ARG0-of (m / make-01 :ARG1 (p / pie)))" ) {
                $('#text').val("four boys making pies");
            }
            $("#prid").hide();
            $("#rid").hide();
        } else {
            baseURL = "http://" + host + "/amr2fred";
            $('#parameters').html("<thead><tr><th>Parameter</th><th>Explanation</th></tr></thead>" +
                    "<tbody><tr><td>&?amr=(x)</td><td>(x) is the AMR encoded with UTF-8</td></tr>" +
                    "<tr><td>&?format=(f)</td><td>(f)select the output format - it is one from ( RDF_XML | RDF_XML_ABBREV | N_TRIPLES | TURTLE | DIGRAPH | png )</td></tr>" +
                    "<tr><td>&?rid_err=ON</td><td>if present it tells to amr2fred to remove nodes with translation errors</td></tr>" +
                    "<tr><td>&?objAsRes</td><td >if present it tells to amr2fred to insert third parts of rdf statements as resources instead of as strings</td></tr>\n\
                    </tbody>" );
            fred = false;
            $("div.Fred").hide();
            $("#prid").show();
            $("#rid").show();
        }
        $('#base').html("Base URI: " + baseURL);

    }





    $(".tooltip").tooltip();
    $('#text').tooltip();
    $('#amr').tooltip();
});



