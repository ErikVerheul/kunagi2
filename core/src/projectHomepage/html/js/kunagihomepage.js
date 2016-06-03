var kunagihp = {};

kunagihp.subscribe = function(baseUrl) {
    var email = prompt('Please enter your email for subscription');
    if (!email) return;
    window.location = baseUrl + email;
}
