



<h1 align="center">Obatin - Self-Medication</h1> <br/>

   Obatin is a machine learning-based application that is able to provide advice or how to use drugs that are freely available at drugstores and drugs that can be purchased without a doctor's prescription. Obatin can be used by teenagers to adults who already have awareness and responsibility in the use of over-the-counter drugs.

<br/>
<br/>

## Obatin Teams - C22-PS334 
<br/>

<table  align="center">
        <thead>
            <tr>
                <th>Name</th>
                <th>Path</th>
				<th>Bangkit ID</th>
				<th>Sosial Media</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Azzahra Novitri Wulandari</td>
				<td>Android</td>
				<td>A2159F1688</td>
				<td></td>
            </tr>
			<tr>
                <td>Ridian Putra</td>
				<td>Android</td>
				<td>A2159F1687</td>
				<td><p><a href="https://www.instagram.com/ridian.p/">Instagram</a></p> & <p><a href="https://github.com/RidianPutra"> Github</a></p></td>
            </tr>
			<tr>
                <td>Raihan Nur Ramadhan</td>
				<td>Machine Learning</td>
				<td>M7010F1098</td>
				<td></td>
            </tr>
			<tr>
                <td>Mohamad Rayhan Adhari Nugroho</td>
				<td>Machine Learning</td>
				<td>M7010F1089</td>
				<td></td>
            </tr>
			<tr>
                <td>Ragil Murdiantoro Aji</td>
				<td>Cloud Computing</td>
				<td>C2159F1689</td>
				<td><p><a href="https://www.linkedin.com/in/ragil-murdiantoro-937195233">LinkedIn</a></p> & <p><a href="https://github.com/Ian-72"> Github</a></p></td>
            </tr>
			<tr>
                <td>Eko Cahya Purnama</td>
				<td>Cloud Computing</td>
				<td>C2152G1670</td>
				<td><p><a href="https://www.linkedin.com/in/ekocahyapurnama/">LinkedIn</a></p> & <p><a href="https://github.com/ekocahyapurnama"> Github</a></p></td>
            </tr>
        </tbody>
    </table>

<br/>
<br/>

## Screenshots


<table  align="center">
        <thead>
            <tr>
                <th>Splash Screen</th>
                <th>Opening Page</th>
				<th>Login Page</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><p align="center"><a href="" target="_blank"><img src="https://cdn.discordapp.com/attachments/889158043470811209/985098134130683924/Splash_Screen.jpeg" width="200"></a></p></td>
				<td><p align="center"><a href="" target="_blank"><img src="https://cdn.discordapp.com/attachments/889158043470811209/985094243011932170/Screenshot_2022-06-10-22-19-24-162_com.ridianputra.obatin.png" width="200"></a></p></td>
				<td><p align="center"><a href="" target="_blank"><img src="https://cdn.discordapp.com/attachments/889158043470811209/985095257182076938/Screenshot_2022-06-11-15-15-32-862_com.ridianputra.obatin.png" width="200"></a></p></td>
            </tr>
        </tbody>
    </table>


<table  align="center">
        <thead>
            <tr>
                <th>Register Page</th>
                <th>Home/Chat Page</th>
				<th>Profile</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><p align="center"><a href="" target="_blank"><img src="https://cdn.discordapp.com/attachments/889158043470811209/985095256951365642/Screenshot_2022-06-11-15-15-39-546_com.ridianputra.obatin.png" width="200"></a></p></td>
				<td><p align="center"><a href="" target="_blank"><img src="https://cdn.discordapp.com/attachments/889158043470811209/985095256628424724/Screenshot_2022-06-11-15-16-11-764_com.ridianputra.obatin.png" width="200"></a></p>
</td>
				<td><p align="center"><a href="" target="_blank"><img src="https://cdn.discordapp.com/attachments/889158043470811209/985095257408548895/Screenshot_2022-06-11-15-16-20-300_com.ridianputra.obatin.png" width="200"></a></p>
</td>
            </tr>
        </tbody>
    </table>

<br/>

## Design Infrastruktur
![Design Infra](https://cdn.discordapp.com/attachments/889158043470811209/985098133895778314/infastruktur.png)

<br/>

## Android Documentation
- [GitHub Repository](https://github.com/RidianPutra/obatin-app)
- [Example Apps](https://drive.google.com/file/d/1INVDIRaa6ji4P6D93zvK_uceu2CX4nGQ/view?usp=sharing)

    ### UI/UX
    - [Figma Design](https://www.figma.com/file/0rK8xLRluzpcM1L3cYLw9y/Obatin-Wireframe?node-id=0%3A1)

<br/>

## Machine Learning Model Installation Locally

These are the steps in installing a machine learning model locally


## Install Requirements
  - [Install Flask Framework](https://phoenixnap.com/kb/install-flask)
  - [Install Matplotlib](https://matplotlib.org/stable/users/installing/index.html)
  - [Install Numpy](https://numpy.org/install/)
  - [Install Sastrawi](https://pypi.org/project/Sastrawi/)
  - [Install Tensorflow](https://www.tensorflow.org/install)

## 1. Run Locally
```bash
git clone https://github.com/raihannur45/obatin.git
```
- How To Run Code
```bash
export FLASK_APP=simulasi.py
python -m flask run
```
```bash
curl http://localhost:5000/bot?text=cara%20minum%20obat%20bisolvon
```

## 2. Run using docker container images
- [Install Docker](https://docs.docker.com/engine/install/)
- Pull the Container Image
```bash
docker pull ekocahya/api-ml:v1
docker run -d -p 5000:5000 ekocahya/api-ml:v1
```
```bash
curl http://localhost:5000/bot?text=cara%20minum%20obat%20bisolvon
```
<br/>

## Installing Obatin-API Server Locally

These are the steps in installing a machine learning model locally


## Install Requirements
  - [Install Node.JS and NPM](https://kinsta.com/blog/how-to-install-node-js/)
  - [Install Memcached](https://www.digitalocean.com/community/tutorials/how-to-install-and-secure-memcached-on-ubuntu-20-04)
  - [Install PM2](https://pm2.keymetrics.io/docs/usage/quick-start/)
  - [Install PostgreSQL](https://www.digitalocean.com/community/tutorials/how-to-install-postgresql-on-ubuntu-20-04-quickstart-id)

## 1. Run Locally:
- Create a new database with a name that you can customize which will later be used to migrate the configuration of the existing tables. For the type of database itself using SQL, you can use other applications for the database of the same type.
- Clone This Repo
```bash
git clone https://github.com/Ian-72/Obatin-API.git
```
- Go to Obatin-API directory
```bash
cd Obatin-API
```
- Install the required modules
```bash
npm install
```
- Make sure to set the .env file if you want to run this code in Production and if you want to run in development set the ./src/config/config.js file
- Run migrate
```bash
npm run migrate
```
- Start API server
    - Run In development
        ```bash
        npm run start-dev
        ```
    - Run In Production
        ```bash
        npm run start-prod
        ```
- The default port set in the Obatin-API server for development is 5000 and for production it is set in the .env file. Make sure the running ports for the machine learning model and API server are different.

## 2. Run using docker container images
- [Install Docker](https://docs.docker.com/engine/install/)
- Pull the Container Image
```bash
docker pull c2159f1689/c22-ps234-cc-api
```
- Run The container
```bash
docker run -d --name cc-api -e NODE_ENV= -e MIGRATE= -e HOST= -e PORT= -e DB_HOST= -e DB_USERNAME= -e DB_PASSWORD= -e DB_DATABASE= -e DB_DIALECT= -e MEMCACHIER_SERVERS= -e ACCESS_TOKEN_AGE= -e ACCESS_TOKEN_KEY= -e REFRESH_TOKEN_KEY= -e ML_API= -p 8080:8080 c2159f1689/c22-ps234-cc-api
```
- Example
```bash
docker run -d --name cc-api -e NODE_ENV=production -e MIGRATE=0 -e HOST=0.0.0.0 -e PORT=8080 -e DB_HOST=34.121.94.31 -e DB_USERNAME=postgres -e DB_PASSWORD=postgres -e DB_DATABASE=obatindb -e DB_DIALECT=postgres -e MEMCACHIER_SERVERS=172.25.0.3:11211 -e ACCESS_TOKEN_AGE=900 -e ACCESS_TOKEN_KEY=cecaf4a11f439bf4699cd36c68de0792a6bc741729cfad5a93f1d367f59257f69c153adb128a359b727dba3ef928ea245ee0ff19e5be44342e9aa9d5df1cf1c7 -e REFRESH_TOKEN_KEY=cb389a2d32bb567baf3a792d5e6253ccea80bb95185f3418fad645001f09bb8cfe295e63a072d8b28daa14e59c317fd69c6ead61275a634791b233e73ca1a51f -e ML_API=http://172.17.0.2:5000 -p 8080:8080 c2159f1689/c22-ps234-cc-api
```
<br/>

# How to Deploy the code to Google Cloud

## Requirements:
  - [Configure the VPC]((https://cloud.google.com/vpc/docs/create-modify-vpc-networks))
    
    All instances that will be created will be on the same network in the same zone, you need to configure the VPC if you want to use the network you created yourself, if you can't use the default network that already exists. For more details about VPC, see [Documentation](https://cloud.google.com/vpc/docs/create-modify-vpc-networks) at this link.
  - [Cloud SQL for PostgreSQL](https://cloud.google.com/sql/docs/postgres/create-instance#console)
    
    Creating a PostgreSQL instance in Google Cloud, make sure to choose the appropriate zone to use to store the instance, or you can visit the [Documentation](https://cloud.google.com/sql/docs/postgres/create-instance#console) link provided by Google for instance creation.
  - [Memorystore - Memcached](https://cloud.google.com/memorystore/docs/memcached/create-instance-console)
    
    Creating an instance of Memcached on Google Cloud, make sure to select the same zone as the other instance to use to store the instance, or you can visit [Documentation](https://cloud.google.com/memorystore/docs/memcached/create-instance-console) link provided by Google for instance creation.
  - [Compute Engine](https://cloud.google.com/compute/docs/instances/create-start-instance)
    
    Creating a virtual machine in Google Cloud, make sure to select the same zone as the other instance to use to store the instance, or you can visit [Documentation](https://cloud.google.com/compute/docs/instances/create-start-instance) link provided by Google for instance creation.

## 1. Configure PostgreSQL Instance:
In this section you have to configure which networks the instance can accept, go to the CLoud SQL page and click the created instance name, then go to the Connections page and search for "Authorized networks", then enter [CIDR](https://cloud.google.com/sql/docs/postgres/authorize-networks?authuser=1&hl=id&_ga=2.213578772.-1277180105.1654861286) from your virtual machine's IP.


## 2. Configure VM's:
At this stage you can configure by imitating the steps in the local installation listed above. And make sure to use the IP and port on the Memcached instance and PostgreSQL instance for use in configuring .env files or Docker commands.

## 3. Set up Firewall Rules:
So that the application can be accessed on the internet, we must open the port for the virtual machine by setting it in the firewall on the VPC network that we are using. For an explanation and how to configure the firewall, you can visit this [Documentation](https://cloud.google.com/vpc/docs/using-firewalls).

<br/>

## Obatin-API Reference

<br/>

#### Authentications

```bash
endpoint /auth
```

|Endpoint              |Method               | Parameter          | Type     | Description                                   |
|:---------------------|:--------------------| :------------------| :------- | :---------------------------------------------|
| /auth                |POST                 | `application/json` | `string` | This endpoint used for user login. Payload contains username and password field, you can fill of username with username or email, after you fill payload and send request to server, server will response 201 and data contains accessToken and refreshToken. |
| /auth                |PUT                  | `application/json` | `string` | This endpoint used for update access token, example when accessToken has expired. Payload contain refreshToken field, after you fill payload and send request to server, server will response 200 and data contains new accessToken. |
| /auth                |DELETE               | `application/json` | `string` | This endpoint used when user logout. Payload contains refreshToken fields, after you fill payload and send request to server, server will response 200 with status success. |

#### Bot

```bash
endpoint /bot
```

|Endpoint              |Method               | Parameter          | Type     | Description                                   |
|:---------------------|:--------------------| :------------------| :------- | :---------------------------------------------|
| /bot                 |POST                 | `application/json` | `string` | This is endpoint where you can talk with Obatin BOT. Payload only contains text field, after you fill text field and send request to server, if success, server will response 200 and data contains response from BOT, else server will response 400 if you send bad payload or server will response 401 that mean you need to login first |
| /bot/message/        |POST                 | `application/json` | `string` | This endpoint is used when the label of the model is known but the name of the drug is not known or has not been entered by the user. |
| /bot/message/reply   |POST                 | `application/json` | `string` | This endpoint is used when the label of the model is known and the drug name is known and will return the answer according to the requested drug name.|


#### Users

```bash
endpoint /users
```

|Endpoint              |Method               | Parameter          | Type     | Description                                   |
|:---------------------|:--------------------| :------------------| :------- | :---------------------------------------------|
| /users               |POST                 | `application/json` | `string` | This endpoint used for user register. Payload contains email, password, passwordConfirmation, username, and fullname field, after you fill payload and send request to server, server will response 201 and data contains user id. |
| /users/profiles      |GET                  | `application/json` | `string` | This endpoint is used to get user profile information based on user name or user email. You must attach email or username in the query parameter, after you fill it and send request to server, server will response 200 and data contains user profiles.|

<br/>
<br/>

