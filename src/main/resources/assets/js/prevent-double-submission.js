export default (form) => {

	if(!form) {
		return;
	}

	let isSubmitted = false;
	const button = Array.from(form.querySelectorAll('[type="submit"]'));

	form.addEventListener('submit', event => {
		event.preventDefault();

		if(isSubmitted) {
			return;
		}

		isSubmitted = true;
		
		if(button.length > 0) {
			button[0].setAttribute('disabled', 'disabled');
		}

		form.submit();
	});
}