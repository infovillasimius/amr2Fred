var data = {}

$(document).ready(function () {

    var host = $(location).attr('host');
    var baseURL = "http://" + host + "/amr2fred";
    $('#base').html("Base URI: " + baseURL);

    checkFred();

    $('#getIt').click(function (event) {
        event.preventDefault();
        get_data()
    });

    $('body').change(function (event) {
        $('#path').html("");
        $("#post").val("")
        checkFred();
    });

    function get_data() {
        data = {}
        var urlo = baseURL;
        var output = $('select#outMode option:selected').attr('value');

        if (output < 6) {

            var amr = $('#amr').val();
            
            amr = amr.replaceAll("\n", " ")
            while (amr.indexOf("  ") !== -1) {
                amr = amr.replaceAll("  ", " ");
            }

            data["amr"] = amr;

            amr = encodeURIComponent(amr);
            amr = amr.replaceAll("%0A", "");

            if ($('#rid')[0].checked === true) {
                urlo += "&?rid_err=ON";
                data["rid"] = true;
            }

            if ($('#altFredUri')[0].checked === true) {
                var altUri = $('#fredUri').val();
                urlo += "&?altFredUri=" + encodeURIComponent(altUri);
                data["alt"] = altUri;
            }

            switch (output) {
                case "0":
                    urlo += "&?format=png" + "&?amr=" + amr;
                    data["format"] = "png";
                    break;
                case "1":
                    urlo += "&?format=RDF_XML" + "&?amr=" + amr;
                    data["format"] = "RDF_XML";
                    break;
                case "2":
                    urlo += "&?format=RDF_XML_ABBREV" + "&?amr=" + amr;
                    data["format"] = "RDF_XML_ABBREV";
                    break;
                case "3":
                    urlo += "&?format=N_TRIPLES" + "&?amr=" + amr;
                    data["format"] = "N_TRIPLES";
                    break;
                case "4":
                    urlo += "&?format=TURTLE" + "&?amr=" + amr;
                    data["format"] = "TURTLE";
                    break;
                case "5":
                    urlo += "&?format=DIGRAPH" + "&?amr=" + amr;
                    data["format"] = "DIGRAPH";
                    break;
                default:
                    urlo += "&?proMode=on" + "&?amr=" + amr;
            }
        } else {
            var amr = $('#amr').val();
            urlo += "&?amr=" + amr + "&?sentence=" + $('#text').val();
            if (output == 7) {
                urlo += "&?commons=png";
            } else if (output == 8) {
                urlo += "&?commons=DIGRAPH";
            }
        }

        var href = '<a href="' + urlo + '" target="_blank">' + urlo + '</a>';
        $('#path').html(href);
        $("#post").val(JSON.stringify(data))
        $("#post_legend").html("JSON to send to " + baseURL +" with a Post request:")
    }

    function checkFred() {
        get_data()
        var output = $('select#outMode option:selected').attr('value');
        if (output < 6) {
            $("#post_div").show();
            baseURL = "http://" + host + "/amr2fred";
            $('#parameters').html("<thead><tr><th>Parameter</th><th>Explanation</th></tr></thead>" +
                "<tbody><tr><td>&?amr=(x)</td><td>(x) is the AMR encoded with UTF-8</td></tr>" +
                "<tr><td>&?format=(f)</td><td>(f)select the output format - it is one from ( RDF_XML | RDF_XML_ABBREV | N_TRIPLES | TURTLE | DIGRAPH | png )</td></tr>" +
                "<tr><td>&?rid_err=ON</td><td>if present it tells amr2fred to remove nodes with translation errors</td></tr>" +
                "<tr><td>&?altFredUri=(u)</td><td >if present it tells amr2fred to use the alternate namespace u instead of Fred default</td></tr>" +
                "</tbody>");
            fred = false;
            $(".text2").hide();
            $("#prid").show();
            $("#ppres").show();
            $('#description').html("<p id=\"description\">What you get is the translated amr in the chosen format.</p>");
        } else if (output >= 6) {
            $("#post_div").hide();
            baseURL = "http://" + host + "/compare";
            $('#parameters').html("<thead><tr><th>Parameter</th><th>Explanation</th></tr></thead>" +
                "<tbody><tr><td>&?amr=(x)</td><td>(x) is the AMR encoded with UTF-8</td></tr>" +
                "<tr><td>&?sentence=(s)</td><td>(s) is the text to send to FRED, encoded with UTF-8 )</td></tr>" +
                "<tr><td>&?commons=(f)</td><td>If present (f) is used to select the output format - it is one from ( DIGRAPH | png )</td></tr>" +
                "</tbody>");
            fred = true;
            $(".text2").show();
            if ($('#text').val() == "" && $('#amr').val() == "(b / boy :quant 4 :ARG0-of (m / make-01 :ARG1 (p / pie)))") {
                $('#text').val("four boys making pies");
            }
            $("#prid").hide();
            $("#ppres").hide();
            $('#description').html("<p id=\"description\">What you get is a text page containing:<br> \n\
                                        1) Percentage values ​​calculated with: one minus the number of triples in the difference between the results of the two programs,\
                                        divided by the number of triples obtained from the first program; <br>\
                                        2) Differences of the two sets of triples;<br>\
                                        3) The set of common triples.</p>");
        }

        if (output > 6) {
            $('#description').html("<p id=\"description\">What you get is an image containing the set of common triples.");
        }

        $('#base').html("Base URI: " + baseURL);

    }

});

