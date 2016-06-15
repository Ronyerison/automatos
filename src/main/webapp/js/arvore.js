$(function() {
	
	var json = document.getElementById('form:arvore');
	var automato = JSON.parse(json.value);

	var graph = new joint.dia.Graph();

	var paper = new joint.dia.Paper({
		el : $('#paper_arv'),
		width : 600,
		height : 1000,
		gridSize : 1,
		model : graph,
		linkConnectionPoint : function(linkView, view) {
			// connection point is always in the center of an element
			return view.model.getBBox().center();
		}
	});

	function state(x, y, label, marcado) {
		var cell;
		if (marcado != true) {

			cell = new joint.shapes.basic.Path({
				position : {
					x : x,
					y : y, 
					parentRelative: true
				},
				size : {
					width : 100,
					height : 40
				},
				attrs : {
					text : {
						text : label,
						'ref-y' : 0.5,
						'y-alignment' : 'middle'
					},
					path : {
						d : 'M 20 0 L 100 0 100 40 20 40 0 20 Z'
					}
				}
			});
		} else {

			cell = new joint.shapes.basic.Path({
				position : {
					x : x,
					y : y
				},
				size : {
					width : 100,
					height : 40
				},
				attrs : {
					text : {
						text : label,
						'ref-y' : 0.5,
						'y-alignment' : 'middle'
					},
					path : {
						d : 'M 20 0 L 100 0 100 40 20 40 0 20 Z'
					}
				}
			});
		}

		graph.addCell(cell);
		return cell;
	}

	function link(source, target) {

		var cell = new joint.dia.Link({
			source : {
				id : source.id
			},
			target : {
				id : target.id
			},
			z : -1,

		});
		graph.addCell(cell);
		return cell;
	}

	
	var estados = new Array();
	
	var i = 0;
	var posX = 225;
	var posY = 10;
	while (i != automato.estados.length) {
		
			var source = state(posX, posY, automato.estados[i].info,
					automato.estados[i].marcado);
			estados[automato.estados[i].info] = source;
		
		posY += 80;
		if(i % 2 === 0){
			posX -= 80;
		}else{
			posX += 80;
		}
		i++;
	}
	i = 0;
	while (i != automato.transicoes.length) {
		if (automato.transicoes[i].origem.info !== automato.transicoes[i].destino.info) {
			var l = link(estados[automato.transicoes[i].origem.info],
					estados[automato.transicoes[i].destino.info],
					automato.transicoes[i].info);
		}
		i++;
	}

});
