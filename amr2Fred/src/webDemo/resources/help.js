$(document).ready(function () {

    var host = $(location).attr('host');
    var baseURL = "http://" + host + "/amr2fred";

    $('#base').html("Base URI: " + baseURL);

    $('#getIt').click(function (event) {
        event.preventDefault();
        var urlo = baseURL;

        if ($('#rid')[0].checked === true) {
            urlo += "&?rid_err=ON";
        }

        if ($('#res')[0].checked === true) {
            urlo += "&?objAsRes";
        }

        var output = $('select#outMode option:selected').attr('value');
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

        urlo = encodeURI(urlo);
        var href = '<a href="' + urlo + '" target="_blank">' + urlo + '</a>';
        $('#path').html(href);
    });

    $('body').change(function (event) {
        $('#path').html("");
    });
  
    $(".tooltip").tooltip();
    $('#text').tooltip();
    $('#amr').tooltip();
});



