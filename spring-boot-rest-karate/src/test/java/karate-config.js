function buildConfig() {
    var config = {
        baseUrl : 'http://localhost:8081'
    };

    var env = karate.env;
    karate.log('Selected Environment: ', env);

    return config;
}