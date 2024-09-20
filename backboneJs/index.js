
// create backbone model

Task = Backbone.Model.extend({
	defaults: {
		todo: "",
	}
		});

// create backbone collection

Tasks = Backbone.Collection.extend({
	
		});

task1 = new Task({todo: "water plants"});
task2 = new Task({todo: "clean garden"});

tasks = new Tasks([task1, task2]);

// create backbone view

TaskView = Backbone.View.extend({

		});
