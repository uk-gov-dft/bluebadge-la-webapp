const del = require('del');
const gulp = require('gulp');
const sass = require('gulp-sass');
const gulpIf = require('gulp-if');
const cssnano = require('gulp-cssnano');
const rollup = require('gulp-better-rollup');
const sourcemaps = require('gulp-sourcemaps');
const autoprefixer = require('gulp-autoprefixer');

/** --------------------------------------------------

 * Configs and paths

-------------------------------------------------- **/

const DEFAULT_ENV = "development";
const BASE_PATH = "./src/main/resources";
const PATH = {
	compiledAssets: {
		css: `${BASE_PATH}/static/css`,
		js: `${BASE_PATH}/static/js`,
	},

	sourceAssets: {
		sass: `${BASE_PATH}/sass/**/*.scss`,
		js: `${BASE_PATH}/js/main.js`
	}
};


/** --------------------------------------------------

 * Utility functions for identifying environment

-------------------------------------------------- **/

const getEnv = () => {
	
	const params = global.process.argv;

	for(let param of params){
		
		if(param.includes("--env")) {

			const env = param.split('=')[1];
			return env ? env : DEFAULT_ENV;

		}
	}

	return DEFAULT_ENV;
}

const isProd = getEnv() === "production";
const isDev = getEnv() === "development";


/** --------------------------------------------------

 * Default task

-------------------------------------------------- **/


gulp.task('clean:css', () => del.sync(['./src/resources/static/css/**']));


gulp.task('sass', ['clean:css'], () => {

	return gulp.src('./src/main/resources/sass/**/*.scss')
		//.pipe(linter)
		.pipe(sass({
			includePaths: [
			    /* 'govuk_modules/govuk_frontend_toolkit/stylesheets', */
			    'node_modules/govuk_template_jinja/assets/stylesheets',
			    'node_modules'
			]
		}).on('error', sass.logError))
		.pipe(gulpIf(isDev, sourcemaps.init()))
		.pipe(autoprefixer()) // needs to go down to iE8 ?
		.pipe(gulpIf(isDev, sourcemaps.write('./')))
		.pipe(gulpIf(isProd, cssnano({
			discardComments: {
				removeAll: true
			}
		})))
		.pipe(gulp.dest(PATH.compiledAssets.css))

});


gulp.task('js', () => {
	gulp.src(PATH.sourceAssets.js)
		//.pipe(linter)
		.pipe(gulpIf(isDev, sourcemaps.init()))
		//.pipe(rollup({plugins: [babel()]}, 'umd'))
		.pipe(rollup())
		.pipe(gulpIf(isDev, sourcemaps.write('./')))
		.pipe(gulp.dest(PATH.compiledAssets.js))
});


gulp.task('default', ['sass', 'js'], () => {
    gulp.watch(PATH.sourceAssets.sass, ['sass']);
    gulp.watch(PATH.sourceAssets.js, ['js']);
});