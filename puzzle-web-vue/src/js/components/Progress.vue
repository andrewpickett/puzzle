<template>
	<div id="cy">

	</div>
</template>

<script>
	import cytoscape from 'cytoscape';
	import dagre from 'cytoscape-dagre';

	export default {
		data() {
			return {
				users: {}
			}
		},
		mounted() {
			const cy = cytoscape({
				container: $('#cy'),
				elements: [
					{ data: { id: '1' } },
					{ data: { id: '2' } },
					{ data: { id: '3' } },
					{ data: { id: '4' } },
					{ data: { id: '5' } },
					{ data: { id: '6' } },
					{ data: { id: '7' } },
					{ data: { id: '8' } },
					{ data: { id: '9' } },
					{ data: { id: '10' } },
					{ data: { id: '11' } },
					{ data: { id: '1-2', source: '1', target: '2' } },
					{ data: { id: '2-3', source: '2', target: '3' } },
					{ data: { id: '3-4', source: '3', target: '4' } },
					{ data: { id: '3-5', source: '3', target: '5' } },
					{ data: { id: '4-5', source: '4', target: '5' } },
					{ data: { id: '5-6', source: '5', target: '6' } },
					{ data: { id: '5-7', source: '5', target: '7' } },
					{ data: { id: '6-8', source: '6', target: '8' } },
					{ data: { id: '7-8', source: '7', target: '8' } },
					{ data: { id: '8-9', source: '8', target: '9' } },
					{ data: { id: '8-10', source: '8', target: '10' } },
					{ data: { id: '8-11', source: '8', target: '11' } }
				],
				style: [
					{
						selector: 'node',
						style: {
							'background-color': '#fff',
							'label': 'data(id)'
						}
					},

					{
						selector: 'edge',
						style: {
							'width': 3,
							'line-color': '#ccc',
							'target-arrow-color': '#ccc',
							'target-arrow-shape': 'triangle'
						}
					}
				]
				//
				// // initial viewport state:
				// zoom: 1,
				// pan: { x: 0, y: 0 },
				//
				// // interaction options:
				// minZoom: 1e-50,
				// maxZoom: 1e50,
				// zoomingEnabled: true,
				// userZoomingEnabled: true,
				// panningEnabled: true,
				// userPanningEnabled: true,
				// boxSelectionEnabled: false,
				// selectionType: 'single',
				// touchTapThreshold: 8,
				// desktopTapThreshold: 4,
				// autolock: false,
				// autoungrabify: false,
				// autounselectify: false,
				//
				// // rendering options:
				// headless: false,
				// styleEnabled: true,
				// hideEdgesOnViewport: false,
				// hideLabelsOnViewport: false,
				// textureOnViewport: false,
				// motionBlur: false,
				// motionBlurOpacity: 0.2,
				// wheelSensitivity: 1,
				// pixelRatio: 'auto'
			});
			cy.use(dagre);
		}
	}
</script>

<style>
	#cy {
		width: 1024px;
		height: 600px;
		display: block;
	}
</style>
