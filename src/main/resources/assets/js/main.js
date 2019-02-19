require('@babel/polyfill');

import All from 'govuk-frontend/all';
import preventDoubleSubmission from "./prevent-double-submission";

All.initAll();

window.onload = () => {
    const forms = Array.from(document.querySelectorAll('[data-prevent-double-submission]'));
    if(forms.length > 0) {
        forms.forEach(form => preventDoubleSubmission(form));
    }
}
