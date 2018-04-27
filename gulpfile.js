const del = require('del');
const gulp = require('gulp');
const sass = require('gulp-sass');
const gulpIf = require('gulp-if');
const cssnano = require('gulp-cssnano');
const browserSync = require('browser-sync');
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
		css: `${BASE_PATH}/compiled/css`,
		js: `${BASE_PATH}/compiled/js`,
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

gulp.task('default', ['sass', 'js']);


gulp.task('clean:css', () => {
	del.sync(['./assets/dist/css/**']);
});


gulp.task('sass', ['clean:css'], () => {

	return gulp.src(PATH.sourceAssets.sass)
		//.pipe(linter)
		.pipe(sass({
			// Added so its easier to import GDS toolkit
			// inside sass file
			includePaths: 'node_modules'
		}))
		.pipe(gulpIf(isDev, sourcemaps.init()))
		.pipe(autoprefixer()) // needs to go upto iE8 ?
		.pipe(gulpIf(isDev, sourcemaps.write('./')))
		.pipe(gulpIf(isProd, cssnano({
			discardComments: {
				removeAll: true
			}
		})))
		.pipe(gulp.dest(PATH.compiledAssets.css))
		// .pipe(browserSync.stream()); // ?

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