function visualizaAutomato() {

	var $tabs = $('.ui-state-default.ui-corner-top'), countTab = 0;

	$tabs.each(function(idx) {
		if ($(this).hasClass('ui-tabs-selected')) {
			countTab = idx;
		}
	});

	var jsonListAutomato = document.getElementById('form:automato').value;
	
	var listAutomato = JSON.parse(jsonListAutomato);
	var automato = listAutomato[countTab];
	

	// var jsonAutomato = document.getElementById('form:automato').value;
	// var automato = JSON.parse(jsonAutomato);
	
	if (($('.paper')[countTab]).children.length == 0) {

		var graph = new joint.dia.Graph();
		console.log($('.paper')[countTab]);

		var paper = new joint.dia.Paper({
			el : $('.paper')[countTab],
			width : 860,
			height : 750,
			gridSize : 1,
			model : graph
		});

		function state(x, y, label, marcado) {
			var cell;
			if (marcado != true) {
				cell = new joint.shapes.fsa.State({
					position : {
						x : x,
						y : y
					},
					size : {
						width : 60,
						height : 60
					},
					attrs : {
						text : {
							text : label,
							fill : '#000000',
							'font-weight' : 'normal'
						},
						'circle' : {
							fill : '#f6f6f6',
							stroke : '#000000',
							'stroke-width' : 2
						}
					}
				});
			} else {
				cell = new joint.shapes.fsa.State({
					position : {
						x : x,
						y : y
					},
					size : {
						width : 60,
						height : 60
					},
					attrs : {
						text : {
							text : label,
							fill : '#000000',
							'font-weight' : 'normal'
						},
						'circle' : {
							fill : '#00FA9A',
							stroke : '#000000',
							'stroke-width' : 2
						}
					}
				});
			}

			graph.addCell(cell);
			return cell;
		}

		function link(source, target, label, vertices) {

			var cell = new joint.shapes.fsa.Arrow({
				source : {
					id : source.id
				},
				target : {
					id : target.id
				},
				labels : [ {
					position : 0.5,
					attrs : {
						text : {
							text : label || '',
							'font-weight' : 'bold'
						}
					}
				} ],
				vertices : vertices || []
			});
			graph.addCell(cell);
			return cell;
		}

		var x = Math.floor((Math.random() * 600) + 100);
		var y = Math.floor((Math.random() * 400) + 100);
		var start = new joint.shapes.fsa.StartState({
			position : {
				x : x,
				y : y
			},
			size : {
				width : 20,
				height : 20
			}
		});
		graph.addCell(start);

		var estadoInicial = state(automato.estadoInicial.info.x,
				automato.estadoInicial.info.y,
				automato.estadoInicial.info.label,
				automato.estadoInicial.marcado);
		var estados = new Array();
		estados[automato.estadoInicial.info.label] = estadoInicial;
		var l = link(start, estadoInicial, '');
		var i = 0;
		while (i != automato.estados.length) {
			if (automato.estadoInicial.info.label != automato.estados[i].info.label) {
				var source = state(automato.estados[i].info.x,
						automato.estados[i].info.y,
						automato.estados[i].info.label,
						automato.estados[i].marcado);
				estados[automato.estados[i].info.label] = source;
			}
			i++;
		}
		i = 0;
		while (i != automato.transicoes.length) {
			if (automato.transicoes[i].origem.info.label != automato.transicoes[i].destino.info.label) {
				var l = link(estados[automato.transicoes[i].origem.info.label],
						estados[automato.transicoes[i].destino.info.label],
						automato.transicoes[i].info);
			} else {
				var pontos = automato.transicoes[i].pontos;
				var l = link(estados[automato.transicoes[i].origem.info.label],
						estados[automato.transicoes[i].destino.info.label],
						automato.transicoes[i].info, [ {
							x : pontos[0].x,
							y : pontos[0].y
						}, {
							x : pontos[1].x,
							y : pontos[1].y
						} ]);
			}
			i++;
		}

	}
}

function getIndexTab() {
	var $tabs = $('.ui-state-default.ui-corner-top'), countTab = 0;

	$tabs.each(function(idx) {
		if ($(this).hasClass('ui-tabs-selected')) {
			countTab = idx;
			return countTab;
		}
	});
}

function limpaDiv() {
	$('.paper').html("");
}

function renderizarPetri() {

	var $tabs = $('.ui-state-default.ui-corner-top'), countTab = 0;

	$tabs.each(function(idx) {
		if ($(this).hasClass('ui-tabs-selected')) {
			countTab = idx;
		}
	});

	var jsonPetri = document.getElementById('form_petri:petri_json').value;
	
	var listPetri = JSON.parse(jsonPetri);
	var petri = listPetri[countTab];
	
	if (($('.paper_petri')[countTab]).children.length == 0) {

		var graph = new joint.dia.Graph();
		console.log($('.paper_petri')[countTab]);

		var paper = new joint.dia.Paper({
			el : $('.paper_petri')[countTab],
			width : 860,
			height : 750,
			gridSize : 10,
			perpendicularLinks: true,
			model : graph
		});

		var pn = joint.shapes.pn;
		
		var pReady = new pn.Place({
		    position: { x: 140, y: 50 },
		    attrs: {
		        '.label': { text: 'ready', fill: '#7c68fc' },
		        '.root' : { stroke: '#9586fd', 'stroke-width': 3 },
		        '.tokens > circle': { fill : '#7a7e9b' }
		    },
		    tokens: 1
		});

		var pIdle = pReady.clone().attr({
		    '.label': { text: 'idle' }
		}).position(140, 260).set('tokens', 2);

		var buffer = pReady.clone().attr({
		    '.label': { text: 'buffer' },
		    '.alot > text': {
		        fill: '#fe854c',
		        'font-family': 'Courier New',
		        'font-size': 20,
		        'font-weight': 'bold',
		        'ref-x': 0.5,
		        'ref-y': 0.5,
		        'y-alignment': -0.5
		    }
		}).position(350, 160).set('tokens', 12);

		var cAccepted = pReady.clone().attr({
		    '.label': { text: 'accepted' }
		}).position(550, 50).set('tokens', 1);

		var cReady = pReady.clone().attr({
		    '.label': { text: 'accepted' }
		}).position(560, 260).set('ready', 3);


		var pProduce = new pn.Transition({
		    position: { x: 50, y: 160 },
		    attrs: {
		        '.label': { text: 'produce', fill: '#fe854f' },
		        '.root' : { fill: '#9586fd', stroke: '#9586fd' }
		    }
		});

		var pSend = pProduce.clone().attr({
		    '.label': { text: 'send' }
		}).position(270, 160);

		var cAccept = pProduce.clone().attr({
		    '.label': { text: 'accept' }
		}).position(470, 160);

		var cConsume = pProduce.clone().attr({
		    '.label': { text: 'consume' }
		}).position(680, 160);


		function link(a, b) {

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
		        }
		    });
		}

		graph.addCell([ pReady, pIdle, buffer, cAccepted, cReady, pProduce, pSend, cAccept, cConsume ]);

		graph.addCell([
		    link(pProduce, pReady),
		    link(pReady, pSend),
		    link(pSend, pIdle),
		    link(pIdle, pProduce),
		    link(pSend, buffer),
		    link(buffer, cAccept),
		    link(cAccept, cAccepted),
		    link(cAccepted, cConsume),
		    link(cConsume, cReady),
		    link(cReady, cAccept)
		]);


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
		    _.each(placesBefore, function(p) { if (p.get('tokens') === 0) isFirable = false; });

		    if (isFirable) {

		        _.each(placesBefore, function(p) {
		            // Let the execution finish before adjusting the value of tokens. So that we can loop over all transitions
		            // and call fireTransition() on the original number of tokens.
		            _.defer(function() { p.set('tokens', p.get('tokens') - 1); });

		            var link = _.find(inbound, function(l) { return l.get('source').id === p.id; });
		            paper.findViewByModel(link).sendToken(V('circle', { r: 5, fill: '#feb662' }).node, sec * 1000);

		            });

		            _.each(placesAfter, function(p) {
		            var link = _.find(outbound, function(l) { return l.get('target').id === p.id; });
		            paper.findViewByModel(link).sendToken(V('circle', { r: 5, fill: '#feb662' }).node, sec * 1000, function() {
		                    p.set('tokens', p.get('tokens') + 1);
		            });

		            });
		    }
		}

		function simulate() {
		    var transitions = [pProduce, pSend, cAccept, cConsume];
		    _.each(transitions, function(t) { if (Math.random() < 0.7) fireTransition(t, 1); });
		    
		    return setInterval(function() {
		        _.each(transitions, function(t) { if (Math.random() < 0.7) fireTransition(t, 1); });
		    }, 2000);
		}

		function stopSimulation(simulationId) {
		    clearInterval(simulationId);
		}

		var simulationId = simulate();
	}	
}
