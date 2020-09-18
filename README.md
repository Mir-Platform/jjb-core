####Для работы jenkins job builder следует ознакомиться с его документацией
https://docs.openstack.org/infra/jenkins-job-builder/

####Если коротко, то:
Требуется установленный плагин на вашем jenkins:
```python
https://plugins.jenkins.io/job-dsl/
```
Установка самого cli производится из pip:

```python
pip install --user jenkins-job-builder
```
Потребуется добавить настройки подключения в конфигурационный файл:
```python
jjb-conf-example.ini
```
Пример запуска:
```python
jenkins-jobs --conf jjb-conf-example.ini update nexus-core-automation/nexus-core-automation.yaml
```