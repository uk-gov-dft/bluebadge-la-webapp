const gulp = require('gulp');
const del = require('del');
const sass = require('gulp-sass');
const gulpIf = require('gulp-if');
const rename = require('gulp-rename');
const uglify = require('gulp-uglify');
const eslint = require('gulp-eslint');
const buffer = require('vinyl-buffer');
const rollup = require('rollup-stream');
const sassLint = require('gulp-sass-lint');
const babel = require('rollup-plugin-babel');
const source = require('vinyl-source-stream');
const sourcemaps = require('gulp-sourcemaps');
const autoprefixer = require('gulp-autoprefixer');
const commonJs = require('rollup-plugin-commonjs');
const resolve = require('rollup-plugin-node-resolve');


const BASE_PATH = './src/main/resources';
const PATH = {
	sourceAssets: {
		sass: `${BASE_PATH}/assets/sass/**/*.scss`,
		js: `${BASE_PATH}/assets/js/main.js`,
		images: `${BASE_PATH}/assets/images`,
		govuk_assets: './node_modules/govuk-frontend/assets/**/*',
		html5_shiv: './node_modules/html5shiv/dist/html5shiv.min.js',
	},

	compiledAssets: {
		css: `${BASE_PATH}/static/css`,
		js: `${BASE_PATH}/static/js`,
		images: `${BASE_PATH}/static`,
		govuk_assets: `${BASE_PATH}/static/govuk`,
	},
};

const babelConfig = {
	presets: [['es2015', { modules: false }]],
	plugins: ['external-helpers'],
	babelrc: false,
	exclude: 'node_modules/**',
};

const getEnv = () => {
	const params = global.process.argv;
	let env = 'prod';

	Object.keys(params).forEach((key) => {
		const param = params[key];

		if (param.includes('-env')) {
			const [, envValue] = param.split('=');
			env = envValue;
		}
	});

	return env;
};

const isProd = getEnv() === 'prod';
const isDev = getEnv() === 'dev';

// Clears CSS directory
gulp.task('clean:css', () => del.sync([PATH.compiledAssets.css]));

// Clears JS directory
gulp.task('clean:js', () => del.sync([PATH.compiledAssets.js]));

// Copies govuk assets from npm folder inside local assets folder
gulp.task('govuk-assets', () => {
	gulp.src([PATH.sourceAssets.govuk_assets])
		.pipe(gulp.dest(PATH.compiledAssets.govuk_assets));
});

// Copies govuk assets from npm folder inside local assets folder
gulp.task('images', () => {
	gulp.src([PATH.sourceAssets.images])
		.pipe(gulp.dest(PATH.compiledAssets.images));
});

// Copies HTML5-Shiv from npm folder inside compiled js folder
gulp.task('html5-shiv', () => {
	gulp.src([PATH.sourceAssets.html5_shiv])
		.pipe(uglify())
		.pipe(rename('html5-shiv.js'))
		.pipe(gulp.dest(PATH.compiledAssets.js));
});

// Lints our SASS code based on rules set inside sass-lint.yml file
gulp.task('sass-lint', () => {
	gulp.src([PATH.sourceAssets.sass])
		.pipe(sassLint({ configFile: './.sass-lint.yml' }))
		.pipe(sassLint.format())
		.pipe(sassLint.failOnError());
});

// Compiles SASS code to CSS.
gulp.task('sass', ['clean:css', 'sass-lint'], () => {
	gulp.src(PATH.sourceAssets.sass)
		.pipe(gulpIf(isDev, sourcemaps.init()))
		.pipe(sass({
			includePaths: ['node_modules'],
			outputStyle: isProd ? 'compressed' : 'expanded',
		}).on('error', sass.logError))
		.pipe(autoprefixer())
		.pipe(gulpIf(isDev, sourcemaps.write('./')))
		.pipe(gulp.dest(PATH.compiledAssets.css));
});

// Lints our JS code based on Airbnb JS rules
gulp.task('js-lint', () => {
	gulp.src([`${BASE_PATH}/js/**/*.js`, '!node_modules/**'])
		.pipe(eslint())
		.pipe(eslint.format())
		.pipe(eslint.failAfterError());
});

gulp.task('js', ['clean:js', 'js-lint', 'html5-shiv'], () => {
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
	.pipe(source(PATH.sourceAssets.js))
	.pipe(buffer())
	.pipe(gulpIf(isDev, sourcemaps.init({ loadMaps: true })))
	.pipe(rename('main.js'))
	.pipe(gulpIf(isProd, uglify()))
	.pipe(gulpIf(isDev, sourcemaps.write('.')))
	.pipe(gulp.dest(PATH.compiledAssets.js));
});

gulp.task('default', ['sass', 'js', 'images', 'govuk-assets'], () => {
	if (isDev) {
		gulp.watch(PATH.sourceAssets.sass, ['sass']);
		gulp.watch(PATH.sourceAssets.js, ['js']);
	}
});
