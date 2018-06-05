const gulp = require('gulp');
const rimraf = require('rimraf');
const sass = require('gulp-sass');
const gulpIf = require('gulp-if');
const uglify = require('gulp-uglify');
const eslint = require('gulp-eslint');
// const stylelint = require('gulp-stylelint');
const uglifyCSS = require('gulp-uglifycss');
const sourcemaps = require('gulp-sourcemaps');
const autoprefixer = require('gulp-autoprefixer');
const buffer = require('vinyl-buffer');
const rollup = require('rollup-stream');
const babel = require('rollup-plugin-babel');
const source = require('vinyl-source-stream');
const commonJs = require('rollup-plugin-commonjs');
const resolve = require('rollup-plugin-node-resolve');

/** --------------------------------------------------

 * Configs and paths

-------------------------------------------------- **/

const BASE_PATH = "./src/main/resources";
const PATH = {
	sourceAssets: {
		sass: `${BASE_PATH}/sass/**/*.scss`,
		js: `${BASE_PATH}/js/main.js`
	},

	compiledAssets: {
		css: `${BASE_PATH}/static/css`,
		js: `${BASE_PATH}/static/js`,
	}
}

const babelConfig = {
	presets: [["es2015", { "modules": false }]],
	plugins: ["external-helpers"],
	babelrc: false,
	exclude: 'node_modules/**'
};


/** --------------------------------------------------

 * Utility functions for identifying environment

-------------------------------------------------- **/

const getEnv = (default_env = "development") => {

	const params = global.process.argv;

	for (let param of params) {

		if (param.includes("--env")) {

			const env = param.split('=')[1];
			return env ? env : default_env;

		}
	}

	return default_env;
}

const isProd = getEnv() === "production";
const isDev = getEnv() === "development";


/** --------------------------------------------------

 * Default task

-------------------------------------------------- **/

gulp.task('clean:css', () => rimraf(PATH.compiledAssets.css, () => { }));
gulp.task('clean:js', () => rimraf(PATH.compiledAssets.js, () => { }));

gulp.task('sass', ['clean:css'], () => {
	return gulp.src(PATH.sourceAssets.sass)
		.pipe(sass({
			includePaths: ['node_modules']
		}).on('error', sass.logError))
		.pipe(gulpIf(isDev, sourcemaps.init()))
		.pipe(autoprefixer())
		.pipe(gulpIf(isProd, uglifyCSS({
			"uglyComments": true
		})))
		.pipe(gulpIf(isDev, sourcemaps.write('./')))
		.pipe(gulp.dest(PATH.compiledAssets.css))
});


gulp.task('lint', () => {
	// ESLint ignores files with "node_modules" paths.
	// So, it's best to have gulp ignore the directory as well.
	// Also, Be sure to return the stream from the task;
	// Otherwise, the task may end before the stream has finished.
	return gulp.src([BASE_PATH + "/js/**/*.js", '!node_modules/**'])
		// eslint() attaches the lint output to the "eslint" property
		// of the file object so it can be used by other modules.
		.pipe(eslint())
		// eslint.format() outputs the lint results to the console.
		// Alternatively use eslint.formatEach() (see Docs).
		.pipe(eslint.format())
		// To have the process exit with an error code (1) on
		// lint error, return the stream and pipe to failAfterError last.
		.pipe(eslint.failAfterError());
});


gulp.task('js', () => {
	rollup({
		format: 'umd',
		legacy: true,
		sourcemap: true,
		input: PATH.sourceAssets.js,
		plugins: [
			resolve(),
			babel(babelConfig),
			commonJs(),
		],
	})
	.pipe(source(PATH.sourceAssets.js, BASE_PATH + "/js/"))
	.pipe(buffer())
	.pipe(sourcemaps.init({ loadMaps: true }))
	.pipe(gulpIf(isDev, uglify()))
	.pipe(sourcemaps.write('.'))
	.pipe(gulp.dest(PATH.compiledAssets.js));
});


gulp.task('default', ['sass', 'lint', 'js'], () => {
	gulp.watch(PATH.sourceAssets.sass, ['sass']);
	gulp.watch(BASE_PATH + "/js/**/*.js", ['lint']);
	gulp.watch(PATH.sourceAssets.js, ['js']);
});