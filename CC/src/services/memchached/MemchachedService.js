const Memcached = require('memjs');

class MemcachedService {
  constructor() {
    this._client = Memcached.Client.create();
  }

  async set(key, data) {
    await this._client.set(key, JSON.stringify(data), { expires: 600 });
    return true;
  }

  async get(key) {
    const result = await this._client.get(key);
    return JSON.parse(result.value);
  }
}

module.exports = MemcachedService;
