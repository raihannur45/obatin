// Import fs module untuk membaca file json
const fs = require('fs');

// Import NlpManager dari module node-nlp.
// Ini digunakan sebagai training, saving, dan loading dan processing.
const { NlpManager } = require('node-nlp');

const botDir = `${__dirname}/../bot`;

// Membuat instance dari class NlpManager
const manager = new NlpManager({ languages: ['en'] });

// Baca semua file intents di dalam folder intents
const files = fs.readdirSync(`${botDir}/intents`);

// Looping melalui file dan parsing string ke objek
// dan pasing it ke manager instance untuk mentrain dan memproses
files.forEach((file) => {
  let data = fs.readFileSync(`${botDir}/intents/${file}`);
  data = JSON.parse(data);

  const intent = file.replace('.json', '');

  const { questions, answers } = data;

  questions.forEach((quest) => {
    manager.addDocument('en', quest, intent);
  });

  answers.forEach((answr) => {
    manager.addAnswer('en', intent, answr);
  });
});

// Membuat sebuah fungsi yang akan bertanggung jawab
// untuk mentraining dan menyimpan manager instance
(async () => {
  // masuk ke folder bot
  process.chdir(`${botDir}`);
  // generate model
  await manager.train();
  manager.save();
})();
