// Import NlpManager dari module node-nlp
// Ini digunakan sebagai training, saving, dan loading dan processing.
const { NlpManager } = require('node-nlp');

class NlpService {
  constructor(botDir) {
    process.chdir(`${botDir}`);
    // eslint-disable-next-line no-underscore-dangle
    this._manager = new NlpManager({ languages: ['en'] });
  }

  async sendMessage({ message }) {
    // eslint-disable-next-line no-underscore-dangle
    this._manager.load();
    const response = await this._manager.process('en', message);
    const { answer } = response;
    if (answer === undefined) {
      return 'Aku tidak dapat memahamimu!';
    }
    return answer;
  }
}

module.exports = NlpService;
