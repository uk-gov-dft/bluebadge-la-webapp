const gulp = require('gulp');
const rimraf = require('rimraf');
const sass = require('gulp-sass');
const gulpIf = require('gulp-if');
const sourcemaps = require('gulp-sourcemaps');
const autoprefixer = require('gulp-autoprefixer');

// const rollup = require('gulp-better-rollup');

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


/** --------------------------------------------------

 * Utility functions for identifying environment

-------------------------------------------------- **/

const getEnv = (default_env = "development") => {
	
	const params = global.process.argv;

	for(let param of params){
		
		if(param.includes("--env")) {

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

gulp.task('clean:css', () => rimraf(PATH.compiledAssets.css, () => {}));
gulp.task('clean:js', () => rimraf(PATH.compiledAssets.js, () => {}));


gulp.task('sass', ['clean:css'], () => {

	return gulp.src(PATH.sourceAssets.sass)
		//.pipe(linter)
		.pipe(sass({
			includePaths: ['node_modules']
		}).on('error', sass.logError))
		.pipe(gulpIf(isDev, sourcemaps.init()))
		.pipe(autoprefixer()) // needs to go down to iE8 ?
		.pipe(gulpIf(isDev, sourcemaps.write('./')))
		// have uninstalled cssnano for the moment
		/* .pipe(gulpIf(isProd, cssnano({
			discardComments: {
				removeAll: true
			}
		})))*/
		.pipe(gulp.dest(PATH.compiledAssets.css))

});

const babelB = require('gulp-babel');
// installed gulp-babel
// set babel-preset-es2015 preset inside package.json
// installed babel core and es2015 preset
gulp.task('js', () => {
	gulp.src(PATH.sourceAssets.js)
		.pipe(babelB({
			presets: ['es2015'],
		}))
		// .pipe(gulpIf(isDev, sourcemaps.init()))
		// .pipe(gulpIf(isDev, sourcemaps.write('./')))
		.pipe(gulp.dest(PATH.compiledAssets.js))
});

const babel = require('rollup-plugin-babel');
const resolve = require('rollup-plugin-node-resolve');
const commonJs = require('rollup-plugin-commonjs');
const rollup = require('rollup-stream');
const source = require('vinyl-source-stream');
const buffer = require('vinyl-buffer');

// installed rollup rollup-stream vinyl-source-stream vinyl-buffer--save-dev
// needs babel-preset-es2015 which is already installed

const rollupJS = (inputFile, options) => {
	return () => {
		return rollup({
			input: options.basePath + inputFile,
			format: options.format,
			sourcemap: options.sourcemap,
			plugins: options.plugins,
			legacy: true,
		})
		// point to the entry file.
		.pipe(source(inputFile, options.basePath))
		// we need to buffer the output, since many gulp plugins don't support streams.
		.pipe(buffer())
		.pipe(sourcemaps.init({loadMaps: true}))
		// some transformations like uglify, rename, etc.
		.pipe(sourcemaps.write('.'))
		.pipe(gulp.dest(options.distPath));
	};
};

// installed npm i rollup-plugin-babel 5 babel-plugin-external-helpers
const babelConfig = {
	presets: [ [ "es2015", { "modules": false } ] ],
	plugins: [ "external-helpers" ],
	babelrc: false,
	exclude: 'node_modules/**'
 };

gulp.task('rollit', rollupJS('main.js', {
	basePath:  BASE_PATH + "/js/",
	format: 'umd',
	distPath: PATH.compiledAssets.js,
	sourcemap: false,
	plugins: [
		resolve(),
		babel(babelConfig),
		commonJs(),
	 ],
	//external: ['@govuk-frontend']
 }));

 gulp.task('test', () => {
	rollup({
		input: PATH.sourceAssets.js,
		format: 'umd',
		legacy: true,
		plugins: [
			resolve(),
			babel(babelConfig),
			commonJs(),
		 ],
	})
	// point to the entry file.
	.pipe(source())
	// we need to buffer the output, since many gulp plugins don't support streams.
	.pipe(buffer())
	// .pipe(sourcemaps.init({loadMaps: true}))
	// some transformations like uglify, rename, etc.
	//.pipe(sourcemaps.write('.'))
	.pipe(gulp.dest(PATH.compiledAssets.js)); 
 })


gulp.task('default', ['sass', 'js'], () => {
    gulp.watch(PATH.sourceAssets.sass, ['sass']);
    gulp.watch(PATH.sourceAssets.js, ['js']);
});