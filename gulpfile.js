const gulp = require('gulp');

gulp.task('default', () => {
	console.log('default task');
});

gulp.task('css', () => {
	console.log('placeholder for css task');
//	throw Error('this is an error');
})

gulp.task('js', () => {
	console.log('placeholder for js task');
})

gulp.task('test', () => {
	console.log('placeholder for test task');
})


gulp.task('ci-task', ['css', 'js', 'test']);