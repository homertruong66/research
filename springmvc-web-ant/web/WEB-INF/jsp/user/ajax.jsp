<script type='text/javascript' src='/yjf/dwr/engine.js'></script>
<script type='text/javascript' src='/yjf/dwr/util.js'></script>
<script type='text/javascript' src='/yjf/dwr/interface/dwrService.js'></script>
<script type="text/javascript">
    function hello() {
      dwrService.hello({
            callback: helloCallBack
      });
    }

    function helloCallBack(dataFromServer) {
      alert(dwr.util.toDescriptiveString(dataFromServer, 3));
    }

    function getUser() {
      dwrService.getUser({
            callback: getUserCallBack
      });
    }

    function getUserCallBack(user) {
      alert(dwr.util.toDescriptiveString(user, 3));
    }
</script>

<style>
.Ajax ul { list-style:none; }
</style>

<div>
    <div class="Ajax">
        <H2> Data loaded by YGS Java Framework</H2>
        <ul>
            <li>DWRService.hello(): ${hello} </li>
            <li>DWRService.getUser(): ${user.username} / ${user.password} / ${user.enabled} </li>
        </ul>
    </div>
    <br/>
    <div class="Ajax">
        <H2> Testing AJAX with Spring MVC</H2>
        <ul>
            <li><a href="#" onclick="hello(); return false;">Say hello</a> </li>
            <li><a href="#" onclick="getUser(); return false;">Retrieve user data</a></li>
        </ul>
    </div>
</div>
