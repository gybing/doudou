(function($){
	var initLayout = function() {
		var hash = window.location.hash.replace('#', '');
		var currentTab = $('ul.navigationTabs a')
							.bind('click', showTab)
							.filter('a[rel=' + hash + ']');
		if (currentTab.size() == 0) {
			currentTab = $('ul.navigationTabs a:first');
		}
		showTab.apply(currentTab.get(0));
		
		var datedate = new Date();
		var date_year = datedate.getYear();
		var date_month = datedate.getMonth();
		var date_day = datedate.getDay();
		var datedatedate = date_year+"_"+date_month+"_"+date_day;
		
		$('#date').DatePicker({
			flat: true,
			date: datedatedate,
			current: datedatedate,
			calendars: 1,
			starts: 1,
			view: 'days'
		});
		var now = new Date();
		now.addDays(-10);
		var now2 = new Date();
		now2.addDays(-5);
		now2.setHours(0,0,0,0);

		$('.inputDatef').DatePicker({
			format:'m/d/Y',
			date: $('#inputDatef').val(),
			current: $('#inputDatef').val(),
			starts: 1,
			position: 'bottom',
			onBeforeShow: function(){
				$('#inputDatef').DatePickerSetDate($('#inputDatef').val(), true);
			},
			onChange: function(formated, dates){
				$('#inputDatef').val(formated);
				if ($('#closeOnSelect input').attr('checked')) {
					$('#inputDatef').DatePickerHide();
				}
			}
		});
		$('.inputDatet').DatePicker({
			format:'m/d/Y',
			date: $('#inputDatet').val(),
			current: $('#inputDatet').val(),
			starts: 1,
			position: 'bottom',
			onBeforeShow: function(){
				$('#inputDatet').DatePickerSetDate($('#inputDatet').val(), true);
			},
			onChange: function(formated, dates){
				$('#inputDatet').val(formated);
				if ($('#closeOnSelect input').attr('checked')) {
					$('#inputDatet').DatePickerHide();
				}
			}
		});
		var now3 = new Date();
		now3.addDays(-4);
		var now4 = new Date()
		$('#widgetCalendar').DatePicker({
			flat: true,
			format: 'd B, Y',
			date: [new Date(now3), new Date(now4)],
			calendars: 3,
			mode: 'range',
			starts: 1,
			onChange: function(formated) {
				$('#widgetField span').get(0).innerHTML = formated.join(' &divide; ');
			}
		});
		var state = false;
		$('#widgetField>a').bind('click', function(){
			$('#widgetCalendar').stop().animate({height: state ? 0 : $('#widgetCalendar div.datepicker').get(0).offsetHeight}, 500);
			state = !state;
			return false;
		});
		$('#widgetCalendar div.datepicker').css('position', 'absolute');
	};
	
	var showTab = function(e) {
		var tabIndex = $('ul.navigationTabs a')
							.removeClass('active')
							.index(this);
		$(this)
			.addClass('active')
			.blur();
		$('div.tab')
			.hide()
				.eq(tabIndex)
				.show();
	};
	
	EYE.register(initLayout, 'init');
})(jQuery)