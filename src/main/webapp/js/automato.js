function visualizaAutomato() {
		
	var $tabs = $('.ui-state-default.ui-corner-top'),
		countTab = 0;
	
	$tabs.each(function(idx){
		if ($(this).hasClass('ui-tabs-selected')) {
			countTab = idx;
		}
	});
	
	var jsonListAutomato = document.getElementById('form:automato').value;
	console.log(jsonListAutomato);
	var listAutomato = JSON.parse(jsonListAutomato);
	var automato = listAutomato[countTab];
	console.log(automato);
	
//	var jsonAutomato = document.getElementById('form:automato').value;
//	var automato = JSON.parse(jsonAutomato);
	
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
			automato.estadoInicial.info.y, automato.estadoInicial.info.label,
			automato.estadoInicial.marcado);
	var estados = new Array();
	estados[automato.estadoInicial.info.label] = estadoInicial;
	var l = link(start, estadoInicial, '');
	var i = 0;
	while (i != automato.estados.length) {
		if (automato.estadoInicial.info.label != automato.estados[i].info.label) {
			var source = state(automato.estados[i].info.x,
					automato.estados[i].info.y, automato.estados[i].info.label,
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

function getIndexTab(){
	var $tabs = $('.ui-state-default.ui-corner-top'),
	countTab = 0;

	$tabs.each(function(idx){
	if ($(this).hasClass('ui-tabs-selected')) {
		countTab = idx;
		return countTab;
	}
	});
}

function limpaDiv(){
	$('.paper').html("");
}
