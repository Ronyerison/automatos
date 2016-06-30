function drawTree() {
	atualizaArvore();
	var json = document.getElementById('form:arvore');
	var objArvore = JSON.parse(json.value);

	var arvore = new joint.dia.Graph();

	var paperArv = new joint.dia.Paper({
		el : $('#paper_arv'),
		width : 600,
		height : 1000,
		gridSize : 1,
		model : arvore,
		linkConnectionPoint : function(linkView, view) {
			// connection point is always in the center of an element
			return view.model.getBBox().center();
		}
	});
	
	function state(x, y, label, duplicated, terminal) {
		var cell;
		if (terminal == true) {

			cell = new joint.shapes.basic.Rect({
				position : {
					x : x,
					y : y,
					parentRelative : true
				},
				size : {
					width : 100,
					height : 40
				},
				attrs : {
					text : {
						text : label,
						'ref-y' : 0.5,
						'y-alignment' : 'middle',
						fill: 'white'
					},
					rect : {
						fill: 'red'
					}
				}
			});
		} else if (duplicated == true){

			cell = new joint.shapes.basic.Rect({
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
						'y-alignment' : 'middle',
						fill: 'white'
					},
					rect : {
						fill: 'blue'
					}
				}
			});
		} else {

			cell = new joint.shapes.basic.Rect({
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
						'y-alignment' : 'middle',
						fill: 'white'
					},
					rect : {
						fill: '#6a6c8a'
					}
				}
			});	
		}

		arvore.addCell(cell);
		return cell;
	}

	function getNewLabel(estado){
		var newLabel = "[";
		for (var i = 0; i < estado.info.stateMatrix.length; i++) {
			if (estado.info.w[i]) {
				newLabel += "w,";
			}else{
				newLabel = newLabel + estado.info.stateMatrix[i] + ",";
			}
		}
		newLabel = newLabel.slice(0, newLabel.length-1) + "]";
		return newLabel;
	}
	
	function aresta(source, target, label) {

		var cell = new joint.dia.Link({
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
			z : -1,

		});
		arvore.addCell(cell);
		return cell;
	}

	var estados = new Array();

	var i = 1;
	var posX = 225;
	var posY = 10;
	var source = state(posX, posY, objArvore.estados[0].info.label,
			objArvore.estados[0].info.duplicated, objArvore.estados[i].info.terminal);
	estados[objArvore.estados[0].info.label] = source;
	while (i != objArvore.estados.length) {
		posY = estados[objArvore.estados[i].info.parentLabel].get('position').y + 80;
		if(posY > 950){
			posY = 950;
		}
		posX = estados[objArvore.estados[i].info.parentLabel].get('position').x + Math.floor((Math.random() * 320) - 160);
		if (posX < 50) {
			posX = 50;
		} else if (posX > 550){
			posX = 550;
		}
		
		var source = state(posX, posY, getNewLabel(objArvore.estados[i]),
				objArvore.estados[i].info.duplicated, objArvore.estados[i].info.terminal);
		estados[objArvore.estados[i].info.label] = source;

		i++;
	}
	i = 0;
	while (i != objArvore.transicoes.length) {
		if (objArvore.transicoes[i].origem.info.label !== objArvore.transicoes[i].destino.info.label) {
			var l = aresta(estados[objArvore.transicoes[i].origem.info.label],
					estados[objArvore.transicoes[i].destino.info.label],
					objArvore.transicoes[i].info.label);
		}
		i++;
	}
//		atualiza();
}
