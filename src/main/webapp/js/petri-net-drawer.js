//Inicializa a RdP
var graph = new joint.dia.Graph();
var paper = new joint.dia.Paper({
    el: $('#paper_petri'),
    width: 600,
    height: 1000,
    gridSize: 10,
    perpendicularLinks: true,
    model: graph
});
var pn = joint.shapes.pn;

//Determinam se os botoes estao ativos
var placeBtnActive = false;
var transitionBtnActive = false;
var linkBtnActive = false;
var tokenBtnActive = false;

//Contador para nomeacao default
var placeCounter = 0;
var transitionCounter = 0;

//Nos do arco
var linkOrigin = undefined;
var linkDestiny = undefined;

//Identificador da simulacao
var simulationId;

function activeMove(){
	placeBtnActive = false;
	transitionBtnActive = false;
	linkBtnActive = false;
	tokenBtnActive = false;
	linkDestiny = undefined;
	linkOrigin = undefined;
}

function activePlace(){
	placeBtnActive = true;
	transitionBtnActive = false;
	linkBtnActive = false;
	tokenBtnActive = false;
	linkDestiny = undefined;
	linkOrigin = undefined;
}

function activeTransition(){
	placeBtnActive = false;
	transitionBtnActive = true;
	linkBtnActive = false;
	tokenBtnActive = false;
	linkDestiny = undefined;
	linkOrigin = undefined;
}

function activeLink(){
	placeBtnActive = false;
	transitionBtnActive = false;
	linkBtnActive = true;
	tokenBtnActive = false;
	linkDestiny = undefined;
	linkOrigin = undefined;
}

function activeTokens(){
	placeBtnActive = false;
	transitionBtnActive = false;
	linkBtnActive = false;
	tokenBtnActive = true;
	linkDestiny = undefined;
	linkOrigin = undefined;
}

//Desliga todos os botoes - Remover quando passar para o primefaces
function turnOffAllButtons(){
	placeBtnActive = false;
	document.getElementById("placeBtn").innerHTML = document.getElementById("placeBtn").innerHTML.replace("On", "Off");
	transitionBtnActive = false;
	document.getElementById("transitionBtn").innerHTML = document.getElementById("transitionBtn").innerHTML.replace("On", "Off");
	linkBtnActive = false;
	document.getElementById("linkBtn").innerHTML = document.getElementById("linkBtn").innerHTML.replace("On", "Off");
	tokenBtnActive = false;
	document.getElementById("tokenBtn").innerHTML = document.getElementById("tokenBtn").innerHTML.replace("On", "Off");
}

//Manipula o comportamento dos botoes - Remover quando passar para o primefaces
function addPlace(){
	if (placeBtnActive == true) {
		placeBtnActive = false;
		document.getElementById("placeBtn").innerHTML = document.getElementById("placeBtn").innerHTML.replace("On", "Off");
	} else {
		turnOffAllButtons();
		placeBtnActive = true;
		document.getElementById("placeBtn").innerHTML = document.getElementById("placeBtn").innerHTML.replace("Off", "On");
	}
}
function addTransition(){
	if (transitionBtnActive == true) {
		transitionBtnActive = false;
		document.getElementById("transitionBtn").innerHTML = document.getElementById("transitionBtn").innerHTML.replace("On", "Off");
	} else {
		turnOffAllButtons();
		transitionBtnActive = true;
		document.getElementById("transitionBtn").innerHTML = document.getElementById("transitionBtn").innerHTML.replace("Off", "On");
	}
}
function addLink(){
	if (linkBtnActive == true) {
		linkBtnActive = false;
		document.getElementById("linkBtn").innerHTML = document.getElementById("linkBtn").innerHTML.replace("On", "Off");
	} else {
		turnOffAllButtons();
		linkBtnActive = true;
		document.getElementById("linkBtn").innerHTML = document.getElementById("linkBtn").innerHTML.replace("Off", "On");
		linkOrigin = undefined;
	}
}
function addToken(){
	if (tokenBtnActive == true) {
		tokenBtnActive = false;
		document.getElementById("tokenBtn").innerHTML = document.getElementById("tokenBtn").innerHTML.replace("On", "Off");

	} else {
		turnOffAllButtons();
		tokenBtnActive = true;
		document.getElementById("tokenBtn").innerHTML = document.getElementById("tokenBtn").innerHTML.replace("Off", "On");
	}
}


function vincularEventos(){
	 $('#paper_petri').click(function(e) {
	    	//Cria um lugar no clique
	    	if (placeBtnActive == true) {
	    		var posX = e.pageX - $(this).position().left -25, posY = e.pageY - $(this).position().top -25;
	        	console.log(posX); console.log(posY);
	    		jPrompt('Defina um nome para o lugar', 'L' + placeCounter++, function(e, value) {
					var place = new pn.Place({
			    		position: { x: posX, y: posY },
			    		attrs: {
				        	'.label': { text: value, fill: '#7c68fc' },
				        	'.root' : { stroke: '#9586fd', 'stroke-width': 3 },
				        	'.tokens > circle': { fill : '#7a7e9b' },
				        	'.alot > text': {
						        fill: '#fe854c',
						        'font-family': 'Courier New',
						        'font-size': 20,
						        'font-weight': 'bold',
						        'ref-x': 0.5,
						        'ref-y': 0.5,
						        'y-alignment': -0.5
						    }
			    		},
			    		id: value,
			    		tokens: 0
					});
					graph.addCell([place]);
					console.log(graph);
					
					//Revincula os eventos de clique em lugar para atualizar a lista de elementos HTML
					$('.element.pn.Place.joint-theme-default > .rotatable').off("click");
					$('.element.pn.Place.joint-theme-default > .rotatable').on("click", function(e) {
						if (linkBtnActive == true) {
					    	if (linkOrigin === undefined) {
					    		linkOrigin = graph.getCell($(this).children('text').children('tspan').text());
					    	} else if (linkDestiny === undefined) {
					    		var element = $(this);
					    		jPrompt('Defina um peso para o arco', '1', function(e, value) {
						    		linkDestiny = graph.getCell(element.children('text').children('tspan').text());
						    		graph.addCell([link(linkOrigin, linkDestiny, value)]);
						    		linkOrigin = undefined;
						    		linkDestiny = undefined;
						    	});
					    	}
					    } else if (tokenBtnActive == true) {
					    	element = graph.getCell($(this).children('text').children('tspan').text())
					    	element.set('tokens', element.get('tokens')+1);
					    }
					});
				});
	    		
	    	}

	    	//Cria uma transicao no clique
	    	if (transitionBtnActive == true) {
	    		var posX = e.pageX - $(this).position().left -5, posY = e.pageY - $(this).position().top -25;
	        	jPrompt('Defina um nome para a transição', 'T' + transitionCounter++, function(e, value) {
					var transition = new pn.Transition({
						position: { x: posX, y: posY },
						attrs: {
							'.label': { text: value, fill: '#fe854f' },
						    '.root' : { fill: '#9586fd', stroke: '#9586fd' }
						},
						id: value
					});
					graph.addCell([transition]);
					//Revincula os eventos de clique em transicao para atualizar a lista de elementos HTML
					$('.element.pn.Transition.joint-theme-default').off("click");
					$('.element.pn.Transition.joint-theme-default').on("click", function(e) {
						if (linkBtnActive == true) {
					    	if (linkOrigin === undefined) {
					    		linkOrigin = graph.getCell($(this).children('text').children('tspan').text());
					    	} else if (linkDestiny === undefined) {
					    		var element = $(this);
					    		jPrompt('Defina um peso para o arco', '1', function(e, value) {
						    		linkDestiny = graph.getCell(element.children('text').children('tspan').text());
						    		console.log(link(linkOrigin, linkDestiny, value));
						    		graph.addCell([link(linkOrigin, linkDestiny, value)]);
						    		linkOrigin = undefined;
						    		linkDestiny = undefined;
						    	});
					    	}
					    }
					});
				});
	    	}

	    });
	 
}

$(document).ready(vincularEventos);

//Converte a RdP para JSON
function convert2JSON(){
	return JSON.stringify(graph);
}

//Filtra a lista de elementos para retornar apenas as transicoes
function checkTransition(element) {
	return element.get('type') == "pn.Transition";
}

//Cria os arcos
function link(a, b, weight) {
    return new pn.Link({
        source: { id: a.id, selector: '.root' },
        target: { id: b.id, selector: '.root' },
        attrs: {
            '.connection': {
                'fill': 'none',
                'stroke-linejoin': 'round',
                'stroke-width': '2',
                'stroke': '#4b4a67'
            }
        },
        labels: [{
        	position: 0.5,
        	attrs: {
        		text: {
        			text: weight || '',
        			'font-weight': 'bold' 
        		}
        	}
        }],
        z: 1
    });
}

//Dispara as transicoes
function fireTransition(t, sec) {

    var inbound = graph.getConnectedLinks(t, { inbound: true });
    var outbound = graph.getConnectedLinks(t, { outbound: true });

    var placesBefore = _.map(inbound, function(link) {
        return graph.getCell(link.get('source').id);
    });
    var placesAfter = _.map(outbound, function(link) {
        return graph.getCell(link.get('target').id);
    });

    var isFirable = true;
    _.each(placesBefore, function(p) {
    	var link = _.find(inbound, function(l) {
    		return l.get('source').id === p.id;
    	});
    	if (p.get('tokens') - parseInt(link.get('labels')[0].attrs.text.text) < 0)
    		isFirable = false;
    });

    if (isFirable) {

        _.each(placesBefore, function(p) {
            // Let the execution finish before adjusting the value of tokens. So that we can loop over all transitions
            // and call fireTransition() on the original number of tokens.
            var link = _.find(inbound, function(l) { return l.get('source').id === p.id; });
            _.defer(function() { p.set('tokens', p.get('tokens') - parseInt(link.get('labels')[0].attrs.text.text)); });

            paper.findViewByModel(link).sendToken(V('circle', { r: 5, fill: '#feb662' }).node, sec * 1000);

        });

        _.each(placesAfter, function(p) {
	        var link = _.find(outbound, function(l) { return l.get('target').id === p.id; });
	        paper.findViewByModel(link).sendToken(V('circle', { r: 5, fill: '#feb662' }).node, sec * 1000, function() {
	                p.set('tokens', p.get('tokens') + parseInt(link.get('labels')[0].attrs.text.text));
	        });
        });
    }
}

//Inicia a simulacao
function simulate() {
	var json = convert2JSON();
	document.getElementById('form:petri').value = json;
	atualiza();
	var transitions = graph.getElements().filter(checkTransition);
    _.each(transitions, function(t) { if (Math.random() < 0.7) fireTransition(t, 1); });
    return setInterval(function() {
        _.each(transitions, function(t) { if (Math.random() < 0.7) fireTransition(t, 1); });
    }, 2000);
}

//Para a simulacao
function stopSimulation(simulationId) {
    clearInterval(simulationId);
}

